package listexmobile.listex.info.listexmobile;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import listexmobile.listex.info.listexmobile.fragments.*;
import listexmobile.listex.info.listexmobile.helpers.State;
import listexmobile.listex.info.listexmobile.helpers.TransitionManager;
import listexmobile.listex.info.listexmobile.models.Good;
import listexmobile.listex.info.listexmobile.networking.CardLoader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static FragmentManager fragmentManager;
    static final String TAG = "GoodsMobile";
    static final boolean selected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment newFragment = new FragmentMain();
        ft.replace(R.id.content_frame, newFragment).commit();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment f = fragmentManager.findFragmentById(R.id.content_frame);
            if (f instanceof FragmentMain)
                super.onBackPressed();
            else {
                Fragment newFragment = new FragmentMain();
                fragmentManager.beginTransaction().replace(R.id.content_frame, newFragment).commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
        State.drawerState.add("NEW");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                if(State.drawerState.size()>0) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();

                    if (id == R.id.nav_scan) {
                        Fragment newFragment = new FragmentLoading();
                        ft.replace(R.id.content_frame, newFragment).commit();
                        openScanActivity();
                    } else if (id == R.id.nav_loc) {
                        Fragment newFragment = new FragmentLoc();
                        ft.replace(R.id.content_frame, newFragment).commit();
                    } else if (id == R.id.nav_search) {
                        Fragment newFragment = new FragmentSearch();
                        ft.replace(R.id.content_frame, newFragment).commit();
                    } else if (id == R.id.nav_home) {
                        Fragment newFragment = new FragmentMain();
                        ft.replace(R.id.content_frame, newFragment).commit();
                    } else if (id == R.id.nav_fav) {
                        Fragment newFragment = new FragmentFav();
                        ft.replace(R.id.content_frame, newFragment).commit();
                    }

                    State.drawerState.clear();
                }
            }
        });

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // TODO Implement transition manager
    public void openGoodCard(Good g)
    {
        FragmentTransaction ft=fragmentManager.beginTransaction();
        FragmentGood card=new FragmentGood();
        card.setContent(g);
        ft.replace(R.id.content_frame, card);
        ft.commitAllowingStateLoss();
    }

    public void openScanActivity() {
        IntentIntegrator integrator = new IntentIntegrator(this);

//        integrator.setCaptureLayout(R.layout.view_finder);
        integrator.setOrientation(1);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        integrator.setScanningRectangle(width, height / 4);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            //FragmentTransaction ft=fragmentManager.beginTransaction();
            //FragmentLoading card=new FragmentLoading();
            //ft.replace(R.id.content_frame, card);
            //ft.commitAllowingStateLoss();

            String scanResult = intent.getStringExtra("barcode");
            if (scanResult != null) {
                Toast.makeText(getBaseContext(), scanResult,
                        Toast.LENGTH_SHORT).show();
                (new CardLoader(scanResult, fragmentManager)).execute();
            } else
                onBackPressed();
        }catch (Exception e)
        {
            Log.d(TAG, e.toString());
            onBackPressed();
        }
    }
}
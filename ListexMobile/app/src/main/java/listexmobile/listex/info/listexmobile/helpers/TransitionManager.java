package listexmobile.listex.info.listexmobile.helpers;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONObject;

import listexmobile.listex.info.listexmobile.MainActivity;
import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.fragments.FragmentGood;
import listexmobile.listex.info.listexmobile.models.Good;

/**
 * Created by oleg-note on 15.05.2016.
 */
public abstract class TransitionManager {
    public static void openGoodCard(Activity a, Good g)
    {
        FragmentTransaction ft=((AppCompatActivity)a).getSupportFragmentManager().beginTransaction();
        FragmentGood card=new FragmentGood();
        card.setContent(g);
        ft.replace(R.id.content_frame, card);
        ft.commitAllowingStateLoss();
    }


}

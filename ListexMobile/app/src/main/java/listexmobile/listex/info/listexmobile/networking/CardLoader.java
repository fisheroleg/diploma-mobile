package listexmobile.listex.info.listexmobile.networking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import listexmobile.listex.info.listexmobile.MainActivity;
import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.fragments.FragmentGood;
import listexmobile.listex.info.listexmobile.fragments.FragmentLoading;
import listexmobile.listex.info.listexmobile.models.Good;

/**
 * Created by oleg-note on 04.05.2016.
 */
public class CardLoader extends AsyncTask<Void, Void, Void> {
    String mScanResult;
    FragmentManager mFM;
    Good mGood;
    static final OkHttpClient client = new OkHttpClient();

    public CardLoader(String scanResult, FragmentManager fm) {
        this.mScanResult = scanResult.trim();
        this.mFM = fm;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Fragment newFragment = new FragmentLoading();
        mFM.beginTransaction().replace(R.id.content_frame, newFragment).commit();
    }

    @Override
    protected void onProgressUpdate(Void... dw) {
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            JSONObject card = getCard();
            if (card == null)
                mGood = null;
            else
                mGood = new Good(card);
            //mGood.loadPhoto();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        openGoodCard(mGood);
    }

    private void openGoodCard(Good g)
    {
        FragmentTransaction ft = mFM.beginTransaction();
        FragmentGood card=new FragmentGood();
        card.setContent(g);
        ft.replace(R.id.content_frame, card);
        ft.commitAllowingStateLoss();
    }

    private JSONObject getCard() {
        try {
            Request request = new Request.Builder()
                    .url("http://ec2-54-201-255-28.us-west-2.compute.amazonaws.com/api/find/"+mScanResult)
                            //.header("Authorization", "Basic NzYxNjkyMDk6TXNXUTlOcmNXQTlsSXVxV2JyWjVwb21XbmxKSXMwNEc=")
                    .build();

            Thread.sleep(3000); //TODO remove dealy

            Response response = client.newCall(request).execute();
            String d = response.body().string();

            JSONObject jo = new JSONObject(d);
            if ( ! jo.getString("code").equals("0") )
                return null;
            return jo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
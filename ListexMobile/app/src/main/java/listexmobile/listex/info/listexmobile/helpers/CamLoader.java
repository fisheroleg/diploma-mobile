package listexmobile.listex.info.listexmobile.helpers;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;

import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.fragments.FragmentGood;
import listexmobile.listex.info.listexmobile.models.Good;

/**
 * Created by oleg-note on 04.05.2016.
 */
public class CamLoader extends AsyncTask<Void, Void, Void> {
    Callable<Void> mCallable;

    public CamLoader(Callable<Void> c) {
        mCallable = c;
    }

    @Override
    protected void onProgressUpdate(Void... dw) {
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            mCallable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
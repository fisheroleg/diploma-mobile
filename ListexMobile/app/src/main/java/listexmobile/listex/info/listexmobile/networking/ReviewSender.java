package listexmobile.listex.info.listexmobile.networking;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.*;
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
public class ReviewSender extends AsyncTask<Void, Void, Void> {
    String mUsername;
    String mBody;
    String mRating;
    String mGoodid;
    Context mContext;
    Callable<Void> mCallAfter;
    boolean mStatus;
    static final OkHttpClient client = new OkHttpClient();

    public ReviewSender(String goodid, String username, String body, String rating, Callable<Void> callAfter, Context context) {
        this.mUsername = username;
        this.mBody = body;
        this.mGoodid = goodid;
        this.mRating = rating;
        this.mContext = context;
        this.mCallAfter = callAfter;
    }

    @Override
    protected void onProgressUpdate(Void... dw) {
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            RequestBody formBody = new FormEncodingBuilder()
                    .add("Goodid", mGoodid)
                    .add("Username", mUsername)
                    .add("Body", mBody)
                    .add("Rating", mRating)
                    .build();

            Request request = new Request.Builder()
                    .url("http://ec2-54-201-255-28.us-west-2.compute.amazonaws.com/api/add")
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            //TODO: handle server error

            mStatus = true;
        } catch (Exception e) {
            e.printStackTrace();
            mStatus = false;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (mStatus) {
            Toast.makeText(mContext,
                    "Thank you for review!", Toast.LENGTH_LONG).show();
            try {
                mCallAfter.call();
            } catch(Exception e) {
                Log.d("ECat", e.getMessage());
            }
        }
        else
        {
            Toast.makeText(mContext,
                    "Error posting review", Toast.LENGTH_LONG).show();
        }
    }
}
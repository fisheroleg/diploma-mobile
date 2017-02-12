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

import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.fragments.FragmentGood;
import listexmobile.listex.info.listexmobile.models.Good;

/**
 * Created by oleg-note on 04.05.2016.
 */
public class CamLoader extends AsyncTask<Void, Void, Void> {
    String mScanResult;
    FragmentManager mFM;
    Good mGood;
    static final OkHttpClient client = new OkHttpClient();

    public CamLoader(String scanResult, FragmentManager fm) {
        this.mScanResult = scanResult.trim();
        this.mFM = fm;
    }

    @Override
    protected void onProgressUpdate(Void... dw) {
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

            mGood = new Good(getCard());
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

            return new JSONObject(d);
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
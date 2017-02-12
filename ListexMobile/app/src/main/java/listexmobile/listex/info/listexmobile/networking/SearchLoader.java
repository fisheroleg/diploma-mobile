package listexmobile.listex.info.listexmobile.networking;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import listexmobile.listex.info.listexmobile.R;

/**
 * Created by oleg-note on 04.05.2016.
 */
public class SearchLoader extends AsyncTask<Void, Void, Void> {
    String mGoodID;
    Context mContext;
    LinearLayout mView;
    JSONArray reviewObject;

    static final OkHttpClient client = new OkHttpClient();

    /**
     * @param id - GoodId or "latest"
     * @param v - layout for filling
     * @param c - context
     */
    public SearchLoader(String id, LinearLayout v, Context c) {
        this.mGoodID = id;
        this.mContext = c;
        this.mView = v;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Request request = new Request.Builder()
                    .url("http://ec2-54-201-255-28.us-west-2.compute.amazonaws.com/api/get/" + mGoodID)
                            //.header("Authorization", "Basic NzYxNjkyMDk6TXNXUTlOcmNXQTlsSXVxV2JyWjVwb21XbmxKSXMwNEc=")
                    .build();

            Response response = client.newCall(request).execute();
            String d = response.body().string();
            this.reviewObject = new JSONArray(d);
        } catch (IOException e) {
            e.printStackTrace();
            this.reviewObject = null;
        } catch (JSONException e) {
            e.printStackTrace();
            this.reviewObject = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (this.reviewObject != null) {
            try {
                if(mView.getChildCount() > 0)
                    mView.removeAllViews();

                for (int i = 0; i < reviewObject.length(); i++) {
                    JSONObject row = reviewObject.getJSONObject(i);
                    View reviewItem = LayoutInflater.from(this.mContext).inflate(R.layout.review_item, null);
                    ((TextView) reviewItem.findViewById(R.id.review_user_name)).setText(row.getString("user"));
                    ((TextView) reviewItem.findViewById(R.id.review_body)).setText(row.getString("review"));
                    ((RatingBar) reviewItem.findViewById(R.id.review_rating))
                            .setRating(Float.parseFloat(row.getString("rating")));
                    ((TextView) reviewItem.findViewById(R.id.review_dtc)).setText(row.getString("DTC"));
                    this.mView.addView(reviewItem);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
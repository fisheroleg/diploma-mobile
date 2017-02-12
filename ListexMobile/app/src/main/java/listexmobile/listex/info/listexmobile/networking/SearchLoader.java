package listexmobile.listex.info.listexmobile.networking;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.fragments.adapters.SearchAdapter;
import listexmobile.listex.info.listexmobile.models.SearchResult;

/**
 * Created by oleg-note on 04.05.2016.
 */
public class SearchLoader extends AsyncTask<Void, Void, Void> {
    Context mContext;
    ListView mView;
    String mQuery;
    JSONArray responseObject;
    ArrayList<SearchResult> mResults;
    TextView mTvMessage;

    static final OkHttpClient client = new OkHttpClient();

    /**
     * @param q - search query
     * @param v - layout for filling
     * @param c - context
     */
    public SearchLoader(String q, ListView v, Context c, TextView tv) {
        this.mContext = c;
        this.mView = v;
        this.mQuery = q;
        this.mTvMessage = tv;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            RequestBody formBody = new FormEncodingBuilder()
                    .add("query", mQuery)
                    .build();

            Request request = new Request.Builder()
                    .url("http://ec2-54-201-255-28.us-west-2.compute.amazonaws.com/api/search/")
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            String d = response.body().string();
            this.responseObject = new JSONArray(d);
            mResults = new ArrayList<>();
            for (int i = 0; i < responseObject.length(); i++) {
                JSONObject row = responseObject.getJSONObject(i);
                View reviewItem = LayoutInflater.from(this.mContext).inflate(R.layout.search_result_item, null);
                mResults.add(new SearchResult(
                        row.getString("name"),
                        row.getString("tmname"),
                        row.getString("goodid"),
                        row.getString("gtin")
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.responseObject = null;
        } catch (JSONException e) {
            e.printStackTrace();
            this.responseObject = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        ((LinearLayout) mTvMessage.getParent()).removeView(mTvMessage);

        if (this.mResults != null) {
            try {
                ((SearchAdapter) mView.getAdapter()).changeDataset(mResults);
                ((SearchAdapter) mView.getAdapter()).notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
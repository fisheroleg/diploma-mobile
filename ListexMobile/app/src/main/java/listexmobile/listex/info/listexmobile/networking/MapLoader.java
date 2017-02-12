package listexmobile.listex.info.listexmobile.networking;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.fragments.FragmentGood;
import listexmobile.listex.info.listexmobile.models.Good;

/**
 * Created by oleg-note on 04.05.2016.
 */
public class MapLoader extends AsyncTask<Void, Void, Void> {
    static final OkHttpClient client = new OkHttpClient();
    Context mContext;
    GoogleMap mMap;
    JSONArray locations;
    String mFilter;
    JSONObject mColorMap;

    public MapLoader(Context c, GoogleMap map, String filter) {
        mContext = c;
        mMap = map;
        mFilter = filter;
    }

    @Override
    protected void onProgressUpdate(Void... dw) {
    }

    @Override
    protected Void doInBackground(Void... params) {
        InputStream is = mContext.getResources().openRawResource(R.raw.loc);
        InputStream im = mContext.getResources().openRawResource(R.raw.colormap);
        Writer writer = new StringWriter();
        Writer colorMapWriter = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            reader = new BufferedReader(new InputStreamReader(im, "UTF-8"));
            while ((n = reader.read(buffer)) != -1) {
                colorMapWriter.write(buffer, 0, n);
            }
            locations = new JSONArray(writer.toString());
            mColorMap = new JSONObject(colorMapWriter.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                im.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if(mMap != null) {
            for (int i = 0; i < locations.length(); i++) {
                try {
                    JSONObject row = (JSONObject) locations.get(i);
                    if ( mFilter != null && ! row.getString("region").toLowerCase().equals(mFilter) )
                        continue;

                    // create marker
                    MarkerOptions marker = new MarkerOptions().position(
                            new LatLng(
                                    Float.parseFloat(row.getString("Lat")),
                                    Float.parseFloat(row.getString("Long"))
                            )
                    ).title(row.getString("ln"));

                    String color;
                    // Get color
                    try {
                        color = mColorMap.getString(row.getString("pn"));
                    } catch (Exception e) {
                        color = "#33B5E6";
                    }

                    // Changing marker icon
                    marker.icon(getMarkerIcon(color));

                    // adding marker
                    mMap.addMarker(marker);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }
}
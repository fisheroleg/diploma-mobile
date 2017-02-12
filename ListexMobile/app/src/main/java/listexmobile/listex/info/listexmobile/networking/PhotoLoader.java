package listexmobile.listex.info.listexmobile.networking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

import listexmobile.listex.info.listexmobile.R;

/**
 * Created by oleg-note on 04.05.2016.
 */
public class PhotoLoader extends AsyncTask<Void, Void, Void> {
    String[] linkImages;
    Context mContext;
    Set<SoftReference<Bitmap>> mReusableBitmaps;
    LinearLayout view;
    public Drawable img;
    String url;

    public PhotoLoader(String[] linkImages, LinearLayout v, Context c) {
        this.linkImages = linkImages;
        this.mContext = c;
        this.view = v;
    }

    public PhotoLoader(String url) {
        this.url = url;
    }

    @Override
    protected void onProgressUpdate(Void... dw) {
        /*try {
            final Iterator<SoftReference<Bitmap>> iterator
                    = mReusableBitmaps.iterator();
            Bitmap item;

            while (iterator.hasNext()) {
                item = iterator.next().get();
                ImageView imageView = new ImageView(this.mContext, null);
                imageView.setAdjustViewBounds(true);
                imageView.setImageBitmap(item);
                imageView.setAdjustViewBounds(true);
                this.view.addView(imageView);
                imageView.setAdjustViewBounds(true);
                //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                //android.view.ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                //layoutParams.width = 300;
                //layoutParams.height = 200;
                //imageView.setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected Void doInBackground(Void... params) {
        /*mReusableBitmaps = Collections.synchronizedSet(new HashSet<SoftReference<Bitmap>>());
        try {
            InputStream in = new java.net.URL(
                            linkImages[0].replace("med", "300x200")).
                            openStream();
            mReusableBitmaps.add(new SoftReference<Bitmap>(BitmapFactory.decodeStream(in)));
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        publishProgress();*/
        try {
            InputStream in = new java.net.URL(
                    url.replace("med", "300x200")).
                    openStream();
            img = Drawable.createFromStream(in, "thumb");
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

    }
}
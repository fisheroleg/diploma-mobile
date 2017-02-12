package listexmobile.listex.info.listexmobile.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.models.Review;

/**
 * Created by oleg-note on 16.05.2016.
 */
public abstract class ReviewFactory {
    public static ArrayList<Review> parseReviews(JSONObject content) {
        try {
            ArrayList<Review> reviews = new ArrayList<>();
            JSONArray rows = content.getJSONArray("reviews");
            for (int i = 0; i < rows.length(); i++) {
                JSONObject row = rows.getJSONObject(i);
                reviews.add(new Review(
                        row.getString("user"),
                        row.getString("review"),
                        Float.parseFloat(row.getString("rating")),
                        row.getString("DTC")
                ));
            }
            return reviews;
        } catch(Exception e) {
            // TODO Handle
            return null;
        }
    }
}

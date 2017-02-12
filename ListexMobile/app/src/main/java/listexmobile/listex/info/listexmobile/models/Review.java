package listexmobile.listex.info.listexmobile.models;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import listexmobile.listex.info.listexmobile.R;

/**
 * Created by oleg-note on 16.05.2016.
 */
public class Review {
    String mUserName;
    String mBody;
    Float mRating;
    String mDTC;
    String mId;

    public Review(String userName, String body, Float rating, String DTC) {
        mBody = body;
        mUserName = userName;
        mRating = rating;
        mDTC = DTC;
        mId = null;
    }

    public Float getRating() {
        return mRating;
    }

    public String getBody() {
        return mBody;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getDTC() {
        return mDTC;
    }

    public String getId() { return mId; }
}

package listexmobile.listex.info.listexmobile.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.ReviewActivity;
import listexmobile.listex.info.listexmobile.fragments.adapters.AttrAdapter;
import listexmobile.listex.info.listexmobile.fragments.adapters.FavAdapter;
import listexmobile.listex.info.listexmobile.models.Attr;
import listexmobile.listex.info.listexmobile.models.Good;
import listexmobile.listex.info.listexmobile.models.Review;
import listexmobile.listex.info.listexmobile.networking.*;
import listexmobile.listex.info.listexmobile.views.FloatingActionButton;

/**
 * Created by oleg-note on 01.11.2015.
 */
public class FragmentGood extends Fragment {
    //String mGoodId;
    Good mGood;
    LinearLayout mLlReviews;

    public void setContent(Good g) {
        this.mGood = g;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            if (mGood == null)
                throw new Exception("Cannot open card");

            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_good,
                    container, false);

            // Display good name
            TextView tvGoodName = (TextView) view.findViewById(R.id.tv_good_name);
            tvGoodName.setText(mGood.getName());
            LinearLayout llAttrs = (LinearLayout) view.findViewById(R.id.ll_attrs_list);

            // Display attrs
            ArrayList<Attr> attrs = mGood.getAttrs();
            if (attrs != null) {
                for (Attr attr : attrs) {
                    View item = AttrAdapter.getView(attr.getName(), attr.getValue(), getContext());
                    llAttrs.addView(item);
                }
            }

            // TODO: display broken photo image
            // Display photos
            final ImageView ivPhoto = (ImageView) view.findViewById(R.id.iv_photo);
            try {
                Picasso.with(getContext())
                        .load(mGood.getPhotos().get(0))
                        .fit()
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .into(ivPhoto);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Display reviews
            // TODO: enable reviews saving
//            ArrayList<Review> reviews = mGood.getReviews();
            mLlReviews = (LinearLayout) view.findViewById(R.id.ll_reviews_list);
/*            if (reviews != null) {
                for (Review review : reviews) {
                    View reviewItem = LayoutInflater.from(getContext()).inflate(R.layout.review_item, null);
                    ((TextView) reviewItem.findViewById(R.id.review_user_name)).setText(review.getUserName());
                    ((TextView) reviewItem.findViewById(R.id.review_body)).setText(review.getBody());
                    llReviews.addView(reviewItem);
                }
            } else {*/
            ReviewLoader rl = new ReviewLoader(Integer.toString(mGood.getId()), mLlReviews, getContext());
            rl.execute();
            //}

            // display rating
            if (mGood.getRating() != null) {
                RatingBar rb = (RatingBar) view.findViewById(R.id.rb_good);
                rb.setRating(mGood.getRating());
                ((TextView) view.findViewById(R.id.tv_rating)).setText(mGood.getRating().toString());
            }

            View.OnClickListener mFavHandler = new View.OnClickListener() {
                public void onClick(View v) {
                    mGood.Save(getContext());
                    Toast.makeText(getContext(), mGood.getName(),
                            Toast.LENGTH_SHORT).show();
                }
            };
            ((ImageButton) view.findViewById(R.id.btn_fav)).setOnClickListener(mFavHandler);

            View.OnClickListener mReviewHandler = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPopup();
                }
            };
            ((android.support.design.widget.FloatingActionButton) view.findViewById(R.id.fab_review)).setOnClickListener(mReviewHandler);

            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return getActivity().getLayoutInflater().inflate(R.layout.fragment_error_getting,
                    container, false);
        }
    }

    private void callPopup() {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View popupView = layoutInflater.inflate(R.layout.activity_review, null);

        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                true);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        final EditText Name = (EditText) popupView.findViewById(R.id.et_name);
        final EditText Body = (EditText) popupView.findViewById(R.id.et_body);
        final RatingBar Rating = (RatingBar) popupView.findViewById(R.id.rb_review);
        final String good = Integer.toString(mGood.getId());

        ((Button) popupView.findViewById(R.id.btn_send_review))
                .setOnClickListener(new View.OnClickListener() {

                    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
                    public void onClick(View arg0) {
                        Callable<Void> c = new Callable<Void>() {
                            public Void call() {
                                ReviewLoader rl = new ReviewLoader(Integer.toString(mGood.getId()), mLlReviews, getContext());
                                rl.execute();
                                return null;
                            }
                        };

                        ReviewSender rs = new ReviewSender(
                                good,
                                Name.getText().toString(),
                                Body.getText().toString(),
                                Float.toString(Rating.getRating()),
                                c,
                                getContext()

                        );

                        rs.execute();

                        popupWindow.dismiss();

                    }

                });

        ((Button) popupView.findViewById(R.id.btn_cancel_review))
                .setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
                    public void onClick(View arg0) {
                        popupWindow.dismiss();
                    }
                });


    }

}

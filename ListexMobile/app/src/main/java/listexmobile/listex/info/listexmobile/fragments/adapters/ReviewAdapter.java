package listexmobile.listex.info.listexmobile.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.models.Review;
import listexmobile.listex.info.listexmobile.models.SearchResult;

/**
 * Created by oleg-note on 26.04.2016.
 */

public class ReviewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mLInflater;
    ArrayList<Review> mReviews;

    public ReviewAdapter(Context context, ArrayList<Review> reviews) {
        this.mContext = context;
        this.mReviews = reviews;
        this.mLInflater = (LayoutInflater) this.mContext
                .getSystemService(this.mContext.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return this.mReviews.size();
    }

    // элемент по позиции
    @Override
    public Review getItem(int position) {
        return mReviews.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return Integer.parseInt(mReviews.get(position).getId());
    }

    public void changeDataset(ArrayList<Review> reviews) {
        mReviews = reviews;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null)
            v = LayoutInflater.from(mContext)
                .inflate(R.layout.search_result_item, null);
        ((TextView) v.findViewById(R.id.search_result_title))
                .setText(this.mReviews.get(position).getUserName());
        ((TextView) v.findViewById(R.id.search_result_tm))
                .setText(this.mReviews.get(position).getBody());
        //((RatingBar) v.findViewById(R.id.list_good_rating))
        //        .setRating(this.mGoods.get(position).getRating());

        return v;
    }
}
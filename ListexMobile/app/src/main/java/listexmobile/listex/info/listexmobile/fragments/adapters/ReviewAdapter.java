package listexmobile.listex.info.listexmobile.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.models.SearchResult;

/**
 * Created by oleg-note on 26.04.2016.
 */

public class ReviewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mLInflater;
    ArrayList<SearchResult> mResults;

    public ReviewAdapter(Context context, ArrayList<SearchResult> results) {
        this.mContext = context;
        this.mResults = results;
        this.mLInflater = (LayoutInflater) this.mContext
                .getSystemService(this.mContext.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return this.mResults.size();
    }

    // элемент по позиции
    @Override
    public SearchResult getItem(int position) {
        return mResults.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return Integer.parseInt(mResults.get(position).getGoodId());
    }

    public void changeDataset(ArrayList<SearchResult> results) {
        mResults = results;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null)
            v = LayoutInflater.from(mContext)
                .inflate(R.layout.search_result_item, null);
        ((TextView) v.findViewById(R.id.search_result_title))
                .setText(this.mResults.get(position).getName());
        ((TextView) v.findViewById(R.id.search_result_tm))
                .setText(this.mResults.get(position).getTMName());
        //((RatingBar) v.findViewById(R.id.list_good_rating))
        //        .setRating(this.mGoods.get(position).getRating());

        return v;
    }
}
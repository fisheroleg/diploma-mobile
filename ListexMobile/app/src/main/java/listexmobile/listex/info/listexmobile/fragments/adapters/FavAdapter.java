package listexmobile.listex.info.listexmobile.fragments.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.models.Attr;
import listexmobile.listex.info.listexmobile.models.Good;

/**
 * Created by oleg-note on 26.04.2016.
 */

public class FavAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mLInflater;
    ArrayList<Good> mGoods;

    public FavAdapter(Context context, ArrayList<Good> goods) {
        this.mContext = context;
        this.mGoods = goods;
        this.mLInflater = (LayoutInflater) this.mContext
                .getSystemService(this.mContext.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return this.mGoods.size();
    }

    // элемент по позиции
    @Override
    public Good getItem(int position) {
        return mGoods.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return mGoods.get(position).getId();
    }

    public void deleteItem(int position) {
        mGoods.remove(position);
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null)
            v = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, null);
        ((TextView) v.findViewById(R.id.list_good_name))
                .setText(this.mGoods.get(position).getName());
        //((TextView) v.findViewById(R.id.list_good_dtc))
        //        .setText(this.mGoods.get(position).getDTC());
        ((RatingBar) v.findViewById(R.id.list_good_rating))
                .setRating(this.mGoods.get(position).getRating());

        return v;
    }
}
package listexmobile.listex.info.listexmobile.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.models.Attr;

/**
 * Created by oleg-note on 26.04.2016.
 */
public abstract class AttrAdapter {

    public static View getView(String name, String value, Context context) {
        View convertView = LayoutInflater.from(context)
                .inflate(R.layout.attr_item, null);
        ((TextView) convertView.findViewById(R.id.attr_name))
                .setText(name);
        ((TextView) convertView.findViewById(R.id.attr_value))
                .setText(value);
        return convertView;
    }
}
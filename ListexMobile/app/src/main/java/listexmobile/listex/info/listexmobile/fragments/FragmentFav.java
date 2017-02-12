package listexmobile.listex.info.listexmobile.fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.fragments.adapters.*;
import listexmobile.listex.info.listexmobile.helpers.DBContract;
import listexmobile.listex.info.listexmobile.helpers.DatabaseHelper;
import listexmobile.listex.info.listexmobile.helpers.TransitionManager;
import listexmobile.listex.info.listexmobile.models.Attr;
import listexmobile.listex.info.listexmobile.models.Good;

/**
 * Created by oleg-note on 01.11.2015.
 */
public class FragmentFav extends Fragment {
    DatabaseHelper mDbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDbHelper = new DatabaseHelper(getContext());

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_fav,
                container, false);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        /*db = mDbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE " + DBContract.GoodEntry.TABLE_NAME);
        db.execSQL(DBContract.SQL_CREATE_GOODS);*/

        String[] projection = {
                DBContract.GoodEntry.COLUMN_NAME_GOOD_ID,
                DBContract.GoodEntry.COLUMN_NAME_NAME
        };

        ArrayList<Good> goods = new ArrayList<>();
        Cursor c = db.query(DBContract.GoodEntry.TABLE_NAME, projection, null, null, null, null, null);
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    String name = c.getString(c.getColumnIndex(DBContract.GoodEntry.COLUMN_NAME_NAME));
                    int id = c.getInt(c.getColumnIndex(DBContract.GoodEntry.COLUMN_NAME_GOOD_ID));
                    goods.add(new Good(id, getContext()));
                }while (c.moveToNext());
            }
            c.close();
        }

        final FavAdapter fa = new FavAdapter(getContext(), goods);

        ListView lvFav = (ListView) view.findViewById(R.id.lv_fav_list);
        lvFav.setAdapter(fa);

        lvFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TransitionManager.openGoodCard(getActivity(), fa.getItem(position));
            }
        });

        lvFav.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                        final int position, long id) {
                final Good good = fa.getItem(position);
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.fav_remove)
                        .setMessage(R.string.fav_ensure)
                        .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                good.delete(getContext());
                                fa.deleteItem(position);
                                fa.notifyDataSetChanged();
                            }

                        })
                        .setNegativeButton(R.string.btn_no, null)
                        .show();

                return true;
            }
        });

        db.close();

        return view;
    }


}

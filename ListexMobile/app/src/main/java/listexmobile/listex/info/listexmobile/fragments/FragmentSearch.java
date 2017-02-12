package listexmobile.listex.info.listexmobile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import listexmobile.listex.info.listexmobile.MainActivity;
import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.fragments.adapters.SearchAdapter;
import listexmobile.listex.info.listexmobile.helpers.CamLoader;
import listexmobile.listex.info.listexmobile.helpers.TransitionManager;
import listexmobile.listex.info.listexmobile.models.Good;
import listexmobile.listex.info.listexmobile.models.SearchResult;
import listexmobile.listex.info.listexmobile.networking.CardLoader;
import listexmobile.listex.info.listexmobile.networking.ReviewLoader;
import listexmobile.listex.info.listexmobile.networking.SearchLoader;

/**
 * Created by oleg-note on 01.11.2015.
 */
public class FragmentSearch extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        SearchView sv = (SearchView) view.findViewById(R.id.search_view);

        final SearchView.OnQueryTextListener osl = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                final ListView lvResults = (ListView) view.findViewById(R.id.lv_search_results);
                lvResults.setAdapter(new SearchAdapter(getContext(), new ArrayList<SearchResult>()));
                lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String GTIN = ((SearchAdapter) lvResults.getAdapter()).getItem(position).getGTIN();
                        (new CardLoader(GTIN, MainActivity.fragmentManager)).execute();
                    }
                });

                SearchLoader rl = new SearchLoader(
                        query,
                        lvResults,
                        getContext(),
                        (TextView) view.findViewById(R.id.search_message)
                );
                rl.execute();

                return false;
            }
        };

        sv.setOnQueryTextListener(osl);

        return view;
    }


}

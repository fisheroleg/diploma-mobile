package listexmobile.listex.info.listexmobile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.concurrent.Callable;

import listexmobile.listex.info.listexmobile.*;
import listexmobile.listex.info.listexmobile.helpers.CamLoader;
import listexmobile.listex.info.listexmobile.networking.ReviewLoader;

/**
 * Created by oleg-note on 01.11.2015.
 */
public class FragmentMain extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ReviewLoader rl = new ReviewLoader(
                "latest",
                ((LinearLayout) view.findViewById(R.id.ll_latest_reviews_list)),
                getContext()
        );
        rl.execute();

        View.OnClickListener mScanHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Callable<Void> c = new Callable<Void>() {
                    public Void call() {
                        return FragmentRec.openScanActivity(getActivity());
                    }
                };

                CamLoader cl = new CamLoader(c);
                cl.execute();
            }
        };
        ((android.support.design.widget.FloatingActionButton) view.findViewById(R.id.fab_scan))
                .setOnClickListener(mScanHandler);

        ((LinearLayout) view.findViewById(R.id.ll_quick_map)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Fragment newFragment = new FragmentLoc();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, newFragment).commit();
            }
        });

        ((LinearLayout) view.findViewById(R.id.ll_quick_search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Fragment newFragment = new FragmentSearch();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, newFragment).commit();
            }
        });

        return view;
    }


}

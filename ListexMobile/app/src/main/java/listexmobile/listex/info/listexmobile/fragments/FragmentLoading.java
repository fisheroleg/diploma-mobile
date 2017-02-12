package listexmobile.listex.info.listexmobile.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

import listexmobile.listex.info.listexmobile.R;

/**
 * Created by oleg-note on 25.05.2016.
 */
public class FragmentLoading extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }
}

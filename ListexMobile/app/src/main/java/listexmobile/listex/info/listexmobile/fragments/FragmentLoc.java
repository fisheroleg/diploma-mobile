package listexmobile.listex.info.listexmobile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import listexmobile.listex.info.listexmobile.MainActivity;
import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.networking.MapLoader;

/**
 * Created by oleg-note on 01.11.2015.
 */
public class FragmentLoc extends Fragment {
    SupportMapFragment mapFragment;
    GoogleMap map;

    MapView mMapView;
    private GoogleMap googleMap;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loc, container, false);

        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(50.5058, 30.4399)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        (new MapLoader(getContext(), googleMap, null)).execute();

        // Inflate the layout for this fragment
        return v;
    }

    private void init() {
    }

    private void finish() {
    }

}

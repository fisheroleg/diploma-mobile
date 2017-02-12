package listexmobile.listex.info.listexmobile.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.util.concurrent.Callable;

import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.helpers.CamLoader;

/**
 * Created by oleg-note on 01.11.2015.
 */
public class FragmentRec extends Fragment {

    Button btn;
    static final String TAG = "GoodsMobile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loading, container, false);

        Callable<Void> c = new Callable<Void>() {
            public Void call() {
                return openScanActivity(getActivity());
            }
        };

        CamLoader cl = new CamLoader(c);
        cl.execute();

        /*IntentIntegrator integrator = new IntentIntegrator(getActivity());

//        integrator.setCaptureLayout(R.layout.view_finder);
        integrator.setOrientation(1);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        integrator.setScanningRectangle(width, height / 4);
        integrator.initiateScan();*/



        /*Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_FORMATS", "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF,CODABAR");
        getActivity().startActivityForResult(intent, 0);	//Barcode Scanner to scan for us
*/
        /*FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_cam, new BarcodeFragment(), "tag").commit();
        fragment = (BarcodeFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_cam);
        fragment.setScanResultHandler(this);
        btn = ((Button)view.findViewById(R.id.cam_scan));
        btn.setEnabled(false);
*/
        // Support for adding decoding type
        //fragment.setDecodeFor(EnumSet.of(Bar.QR_CODE));
        return view;
    }

    public static Void openScanActivity(FragmentActivity a)
    {
        IntentIntegrator integrator = new IntentIntegrator(a);

        // integrator.setCaptureLayout(R.layout.view_finder);
        integrator.setOrientation(1);
        int width = a.getResources().getDisplayMetrics().widthPixels;
        int height = a.getResources().getDisplayMetrics().heightPixels;
        integrator.setScanningRectangle(width, height / 4);
        integrator.initiateScan();

        return null;
    }

}

package com.software.anson.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.software.anson.myapplication.bluetooth.DeviceScanActivity;

/**
 * Created by Anson on 2016/7/3.
 */
public class FragmentDevice extends Fragment {
    private Button bt1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_device, container, false);
        bt1 = (Button) v.findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DeviceScanActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}

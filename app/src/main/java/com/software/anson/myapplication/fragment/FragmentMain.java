package com.software.anson.myapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ls.widgets.map.MapWidget;
import com.software.anson.myapplication.R;

import java.lang.reflect.Field;

/**
 * Created by Anson on 2016/7/3.
 */
public class FragmentMain extends Fragment{
    private static final String TAG = "FragmentMain";
    private TextView tv_from, tv_name, tv_destination, tv_shift, tv_id, tv_timetable, tv_seat, tv_room, person_location, baggage ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_main, container, false);

//        mToolbar1 = (Toolbar) v.findViewById(R.id.toolbar_main);
//        // toolbar_device.setLogo(R.drawable.ic_launcher);
//        mToolbar1.setTitle("");// 标题的文字需在setSupportActionBar之前，不然会无效
//        // toolbar_device.setSubtitle("");
//        mToolbar1.setTitleTextColor(R.color.white);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar1);

        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.rootLayout);
        layout.setBackground(getResources().getDrawable(R.drawable.ad));
        tv_destination = (TextView) v.findViewById(R.id.tv_destination);
        tv_destination.setText("");

        tv_from = (TextView) v.findViewById(R.id.tv_from);
        tv_from.setText("");

        tv_room = (TextView) v.findViewById(R.id.tv_room);
        tv_room.setText("无");

        tv_seat = (TextView) v.findViewById(R.id.tv_seat);
        tv_seat.setText("无");

        tv_shift = (TextView) v.findViewById(R.id.tv_shift);
        tv_shift.setText("用户尚未登录");

        tv_timetable = (TextView) v.findViewById(R.id.tv_timetable);
        tv_timetable.setText("无");

//        person_location = (TextView) v.findViewById(R.id.person_location);
//        person_location.setText("          ");
//
//        baggage = (TextView) v.findViewById(R.id.baggage);
//        baggage.setText("          ");

        SharedPreferences mShared = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        /**拿到SharedPreferences中保存的数值 第二个参数为如果SharedPreferences中没有保存就赋一个默认值**/
        String result = mShared.getString("ID", null);
        if (result != null){

            String Waiting_Room = mShared.getString("Waiting_Room",null);
            tv_room.setText(Waiting_Room);

            String Start_From =  mShared.getString("Start_From",null);
            tv_from.setText(Start_From);

            String Destination = mShared.getString("Destination",null);
            tv_destination.setText(Destination);

            String Shift = mShared.getString("Shift",null);
            tv_shift.setText(Shift);

            String Timetable = mShared.getString("Timetable",null);
            tv_timetable.setText(Timetable);

            String Seat = mShared.getString("Seat",null);
            tv_seat.setText(Seat);

//            person_location.setText("您在第7车厢");
//
//            baggage.setText("行李在第7车厢");
            //除去logo
            Class<?> c = null;
            try {

                c = Class.forName("com.ls.widgets.map.utils.Resources");
                Object obj = c.newInstance();

                Field field = c.getDeclaredField("LOGO");
                field.setAccessible(true);

                field.set(obj, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            layout.setBackground(getResources().getDrawable(R.drawable.lucency));
            MapWidget map = new MapWidget(getActivity(), "train", 11);

            layout.addView(map, 0);
        }

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

    }

}

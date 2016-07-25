package com.software.anson.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Anson on 2016/7/3.
 */
public class FragmentMain extends Fragment {
    private TextView tv_from, tv_name, tv_destination, tv_shift, tv_id, tv_timetable, tv_seat, tv_room ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_main, container, false);

        tv_destination = (TextView) v.findViewById(R.id.tv_destination);
        tv_destination.setText("Ŀ�ĵ�");

        tv_from = (TextView) v.findViewById(R.id.tv_from);
        tv_from.setText("������");

        tv_id = (TextView) v.findViewById(R.id.tv_id);
        tv_id.setText("���֤��");

        tv_name = (TextView) v.findViewById(R.id.tv_name);
        tv_name.setText("����");

        tv_room = (TextView) v.findViewById(R.id.tv_room);
        tv_room.setText("����");

        tv_seat = (TextView) v.findViewById(R.id.tv_seat);
        tv_seat.setText("��λ��");

        tv_shift = (TextView) v.findViewById(R.id.tv_shift);
        tv_shift.setText("�г����");

        tv_timetable = (TextView) v.findViewById(R.id.tv_timetable);
        tv_timetable.setText("����ʱ��");

        SharedPreferences mShared = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        /**�õ�SharedPreferences�б������ֵ �ڶ�������Ϊ���SharedPreferences��û�б���͸�һ��Ĭ��ֵ**/
        String result = mShared.getString("result", null);
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

            String Name = mShared.getString("Name",null);
            tv_name.setText(Name);

            String ID = mShared.getString("ID",null);
            tv_id.setText(ID);
        }

        return v;
    }
}

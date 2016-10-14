package com.software.anson.myapplication.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.software.anson.myapplication.R;
import com.software.anson.myapplication.http.HttpCommand;
import com.software.anson.myapplication.model.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anson on 2016/8/7.
 */

//设备适配器
public class DeviceAdapter extends BaseAdapter {

    private ArrayList<Device> list;
    private Context context;
    private LayoutInflater inflater;

    public DeviceAdapter(Context context, List<Device> list){
        this.list = (ArrayList<Device>) list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    public DeviceAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Device> data){
        this.list = (ArrayList<Device>) data;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.listitem_device,null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        final Device device = list.get(position);
        holder.tv_devicename.setText(device.getDeviceName("name"));
        //holder.iv_battery.setImageDrawable(context.getResources(),R.drawable.0);

        //判断设备电量
        if (device.getBattery() == 0){
            holder.iv_battery.setImageResource(R.drawable.battery_0);
        } else if (device.getBattery() == 1){
            holder.iv_battery.setImageResource(R.drawable.battery_1);
        }else if (device.getBattery() == 2){
            holder.iv_battery.setImageResource(R.drawable.battery_2);
        }else if (device.getBattery() == 3){
            holder.iv_battery.setImageResource(R.drawable.battery_3);
        }else if (device.getBattery() == 4){
            holder.iv_battery.setImageResource(R.drawable.battery_4);
        }

        //判断设备是否开启
        //if (device.getHeart() == 0){
           // holder.connect_state.setImageResource(R.drawable.connect_failed);
        //}else if (device.getHeart() == 1){
            holder.connect_state.setImageResource(R.drawable.connect_icon);
       // }

        holder.swith.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            String url = "http://115.159.38.62:3001/bd/device/appOrder";
            String command_open_warming = "~ZJNU"+ device.getDeviceName("name") +"03t0";
            String command_close_warming = "~ZJNU" + device.getDeviceName("name") + "03s1";
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    new HttpCommand(url,command_open_warming).start();
                } else {
                    new HttpCommand(url,command_close_warming).start();
                }
            }
        });

        return convertView;
    }

    class Holder{
        private TextView tv_devicename;
        private ImageView iv_battery;
        private Switch swith;
        private ImageView connect_state;

        public Holder(View view){
            tv_devicename = (TextView) view.findViewById(R.id.tv_deviceid);
            iv_battery = (ImageView) view.findViewById(R.id.iv_battery);
            swith = (Switch) view.findViewById(R.id.switch1);
            connect_state = (ImageView) view.findViewById(R.id.connect_state);
        }
    }
}

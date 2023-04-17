package com.example.myappone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myappone.R;
import com.example.myappone.tool.StatusBar;
import com.example.myappone.tool.start;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.newland.nle_sdk.responseEntity.DeviceInfo;
import cn.com.newland.nle_sdk.responseEntity.base.BasePager;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;


public class devices_Activity extends AppCompatActivity {

    public NetWorkBusiness business;
    devices_list devices_list;
    ListView list;
    Timer timer;
    boolean is = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar statusBar = new StatusBar(devices_Activity.this);
        statusBar.setColor(R.color.transparent);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_devices);

        timer = new Timer();

        list = this.findViewById(R.id.devices_list);

        //初始化界面控件

        devices_Activity.this.business = new NetWorkBusiness(start.TOKEN, start.URL);


        Task task = new Task();
        timer.schedule(task, 100, 3000);
    }


    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        timer = null;
        Log.i("TAG", "onStop: 线程取消");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Task task = new Task();
        timer = new Timer();
        timer.schedule(task,100,3000);

        Log.i("TAG", "onRestart: 线程重新启动");
    }

    class devices_list extends BaseAdapter {

        Context context;
        LayoutInflater layoutInflater;
        List<Map<String, Object>> data;

        public devices_list(Context context, List<Map<String, Object>> data) {
            this.context = context;
            this.data = data;
            layoutInflater = LayoutInflater.from(context);
        }

        public void Up_data(Context context, List<Map<String, Object>> data) {
            this.context = context;
            this.data = data;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.devices_item, null);
            }

            TextView device_name = view.findViewById(R.id.device_name);
            TextView device_id = view.findViewById(R.id.device_id);
            TextView device_apitag = view.findViewById(R.id.device_apitag);
            TextView device_key = view.findViewById(R.id.device_key);
            TextView device_tcp = view.findViewById(R.id.device_tcp);
            TextView device_data = view.findViewById(R.id.device_data);
            ImageView device_lianjie = view.findViewById(R.id.device_lianjie);

            device_name.setText(Objects.requireNonNull(data.get(i).get("name")).toString());
            device_id.setText("设备ID：" + data.get(i).get("id"));
            device_apitag.setText("设备标识：" + data.get(i).get("apitag"));
            device_key.setText("传输密钥：" + data.get(i).get("key"));
            device_tcp.setText("传输协议：" + data.get(i).get("tcp"));
            if (Objects.equals(data.get(i).get("is"), "true")) {
                device_lianjie.setBackgroundResource(R.drawable.zx);
            } else device_lianjie.setBackgroundResource(R.drawable.bzx);

            device_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    start.DeviceID = data.get(i).get("id").toString();
                    Intent intent = new Intent(devices_Activity.this, Device_Sensor.class);
                    start.Device_zx = data.get(i).get("is").toString();
                    timer.purge();
                    startActivity(intent);
                }
            });

            return view;
        }


    }


    class Task extends TimerTask {

        @Override
        public void run() {

            devices_Activity.this.business.getDeviceFuzzy("", "", "", "", "", start.ProjectId, "", "2020-10-01", start.get_project_time(), "",
                    new NCallBack<BaseResponseEntity<BasePager<DeviceInfo>>>(getApplicationContext()) {
                        @Override
                        protected void onResponse(BaseResponseEntity<BasePager<DeviceInfo>> response) {
                            List<DeviceInfo> infos = response.getResultObj().getPageSet();

                            List<Map<String, Object>> data = new ArrayList<>();
                            HashMap<String, Object> map;

                            for (int i = 0; i < infos.size(); i++) {
                                map = new HashMap<>();
                                map.put("name", infos.get(i).getName());
                                map.put("id", infos.get(i).getDeviceID());
                                map.put("apitag", infos.get(i).getTag());
                                map.put("key", infos.get(i).getProjectIdOrTag());
                                map.put("tcp", infos.get(i).getProtocol());
                                map.put("is", infos.get(i).getIsOnline());
                                Log.i("TAG", "onResponse: " + infos.get(i).getIsOnline());
                                data.add(map);
                            }

                            if (devices_list == null) {
                                devices_list = new devices_list(devices_Activity.this, data);
                                list.setAdapter(devices_list);
                            } else {
                                devices_list.Up_data(devices_Activity.this, data);
                            }

                        }
                    });

        }

    }


}
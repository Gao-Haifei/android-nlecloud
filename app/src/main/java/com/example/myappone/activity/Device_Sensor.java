package com.example.myappone.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myappone.R;
import com.example.myappone.list_item.sensor_list;
import com.example.myappone.tool.StatusBar;
import com.example.myappone.tool.start;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.newland.nle_sdk.responseEntity.DeviceDatas;
import cn.com.newland.nle_sdk.responseEntity.SensorInfo;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;

public class Device_Sensor extends AppCompatActivity {
    Timer timer;
    ListView devices_list;

    sensor_list sensor_list;

    NetWorkBusiness business;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar statusBar = new StatusBar(Device_Sensor.this);
        statusBar.setColor(R.color.transparent);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_devices);

        timer = new Timer();
        devices_list = findViewById(R.id.devices_list);

        Device_Sensor.this.business = new NetWorkBusiness(start.TOKEN, start.URL);

        if (start.Device_zx.equals("true")){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Device_Sensor.this.business.getSensors(start.DeviceID, "", new NCallBack<BaseResponseEntity<List<SensorInfo>>>(getApplicationContext()) {
                        @Override
                        protected void onResponse(BaseResponseEntity<List<SensorInfo>> response) {
                            List<SensorInfo> infos = response.getResultObj();
                            List<Map<String,Object>> data = new ArrayList<>();
                            HashMap<String,Object> map;

                            for (int i = 0; i < infos.size(); i++) {
                                map = new HashMap<>();
                                map.put("name",infos.get(i).getName());
                                map.put("apitag",infos.get(i).getApiTag());
                                map.put("value",infos.get(i).getValue());
                                map.put("time",start.get_time());
                                map.put("type",infos.get(i).getOperType());
                                data.add(map);
                            }
                            if (sensor_list == null){
                                sensor_list = new sensor_list(Device_Sensor.this,data);
                                devices_list.setAdapter(sensor_list);
                            }
                            else {
                                sensor_list.UpData(Device_Sensor.this,data);
                            }

                        }
                    });
                }
            },1000,5000);
        }
        else {
            Device_Sensor.this.business.getSensors(start.DeviceID, "", new NCallBack<BaseResponseEntity<List<SensorInfo>>>(getApplicationContext()) {
                @Override
                protected void onResponse(BaseResponseEntity<List<SensorInfo>> response) {
                    List<SensorInfo> infos = response.getResultObj();
                    List<Map<String,Object>> data = new ArrayList<>();
                    HashMap<String,Object> map;

                    for (int i = 0; i < infos.size(); i++) {
                        map = new HashMap<>();
                        map.put("name",infos.get(i).getName());
                        map.put("apitag",infos.get(i).getApiTag());
                        map.put("value",infos.get(i).getValue());
                        map.put("time",start.get_time());
                        map.put("type",infos.get(i).getOperType());
                        data.add(map);
                    }
                    if (sensor_list == null){
                        sensor_list = new sensor_list(Device_Sensor.this,data);
                        devices_list.setAdapter(sensor_list);
                    }
                    else {
                        sensor_list.UpData(Device_Sensor.this,data);
                    }

                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

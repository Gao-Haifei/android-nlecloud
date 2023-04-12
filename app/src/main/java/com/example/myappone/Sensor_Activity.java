package com.example.myappone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.newland.nle_sdk.responseEntity.SensorInfo;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;

public class Sensor_Activity extends AppCompatActivity {

    NetWorkBusiness business;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar statusBar = new StatusBar(Sensor_Activity.this);
        statusBar.setColor(R.color.transparent);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_sensor);
        LinearLayout sensor_data_one_all = findViewById(R.id.sensor_data_one_all);
        LinearLayout sensor_data_one = findViewById(R.id.sensor_data_one);
        LinearLayout sensor_data_execute = findViewById(R.id.sensor_data_execute);
        LinearLayout sensor_data_all = findViewById(R.id.sensor_data_all);
        LinearLayout sensor_data_null = findViewById(R.id.sensor_data_null);
        ListView list_id = findViewById(R.id.sensor_data_list);
        LinearLayout sensor_data_led = findViewById(R.id.sensor_data_led);
        EditText sensor_data_led_txt = findViewById(R.id.sensor_data_led_txt);
        Button sensor_data_led_send = findViewById(R.id.sensor_data_led_send);
        TextView sensor_data_led_name = findViewById(R.id.sensor_data_led_name);
        TextView sensor_data_led_type = findViewById(R.id.sensor_data_led_type);

        TextView sensor_data_name = findViewById(R.id.sensor_data_name);

        TextView sensor_data_sen_state = findViewById(R.id.sensor_data_sen_state);
        TextView sensor_data_sen_value = findViewById(R.id.sensor_data_sen_value);
        TextView sensor_data_sen_type = findViewById(R.id.sensor_data_sen_type);
        TextView sensor_data_sen_api = findViewById(R.id.sensor_data_sen_api);
        TextView sensor_data_sen_time = findViewById(R.id.sensor_data_sen_time);
        TextView sensor_data_sen_deviceid = findViewById(R.id.sensor_data_seb_deviceid);

        Switch sensor_data_exe_switch = findViewById(R.id.sensor_data_exe_switch);
        Button sensor_data_exe_swt2 = findViewById(R.id.sensor_data_exe_swt2);
        Button sensor_data_exe_swt1 = findViewById(R.id.sensor_data_exe_swt1);
        Button sensor_data_exe_swt = findViewById(R.id.sensor_data_exe_swt);
        TextView sensor_data_exe_type = findViewById(R.id.sensor_data_exe_type);
        TextView sensor_data_exe_time = findViewById(R.id.sensor_data_exe_time);
        TextView sensor_data_exe_api = findViewById(R.id.sensor_data_exe_api);
        TextView sensor_data_exe_deviceid = findViewById(R.id.sensor_data_exe_deviceid);

        Sensor_Activity.this.business = new NetWorkBusiness(start.TOKEN,start.URL);




        if (Sensor_Activity.this.business == null){
            Log.i("无",start.ApiTag);
    } else {
            Log.i("有",start.ApiTag);
            if (start.ApiTag == "All_Sensor"){
                Sensor_Activity.this.business.getAllSensors(start.ProjectId,
                        new NCallBack<BaseResponseEntity<List<SensorInfo>>>(getApplicationContext()) {
                            @Override
                            protected void onResponse(BaseResponseEntity<List<SensorInfo>> response) {
                                List<SensorInfo> sensorInfos = response.getResultObj();
                                if(sensorInfos == null){
                                    sensor_data_null.setVisibility(View.VISIBLE);
                                    sensor_data_all.setVisibility(View.GONE);
                                    sensor_data_one.setVisibility(View.GONE);
                                    sensor_data_one_all.setVisibility(View.GONE);
                                }else {
                                    sensor_data_null.setVisibility(View.GONE);
                                    sensor_data_one.setVisibility(View.GONE);
                                    sensor_data_one_all.setVisibility(View.GONE);
                                    sensor_data_all.setVisibility(View.VISIBLE);
                                    List<Map<String,Object>> data =new ArrayList<Map<String,Object>>();
                                    Map<String,Object> map =new HashMap<String,Object>();

                                    for (SensorInfo item :sensorInfos){
                                        map.put("name",item.getName());
                                        map.put("price",item.getValue());
                                        map.put("type",item.getDataType());
                                        map.put("api",item.getApiTag());
                                        map.put("time",item.getRecordTime());
                                        map.put("projectid",item.getDeviceID());
                                        map.put("deviceid",item.getDeviceID());
                                        data.add(map);
                                        map = new HashMap<String, Object>();
                                    }
                                    List_item list_data = new List_item(Sensor_Activity.this,data);
                                    list_id.setAdapter(list_data);
                                }
                            }
                        });
            }else{
                Sensor_Activity.this.business.getSensor(start.DeviceID, start.ApiTag,
                        new NCallBack<BaseResponseEntity<SensorInfo>>(getApplicationContext()) {
                    @Override
                    protected void onResponse(BaseResponseEntity<SensorInfo> response) {
                        SensorInfo sensorInfo = response.getResultObj();
                        if (sensorInfo == null){
                            sensor_data_null.setVisibility(View.VISIBLE);
                            sensor_data_one.setVisibility(View.GONE);
                            sensor_data_one_all.setVisibility(View.GONE);
                        }else {
                            Log.i("datatype",String.valueOf(sensorInfo.getOperType()));

                            if (sensorInfo.getOperType() == 0){


                                sensor_data_one_all.setVisibility(View.VISIBLE);
                                sensor_data_null.setVisibility(View.GONE);
                                sensor_data_execute.setVisibility(View.GONE);
                                sensor_data_one.setVisibility(View.VISIBLE);
                                sensor_data_name.setText(sensorInfo.getName());
                                sensor_data_sen_value.setText(sensorInfo.getValue()+" "+sensorInfo.getUnit());
                                sensor_data_sen_type.setText("传感器");
                                sensor_data_sen_api.setText(sensorInfo.getApiTag());
                                sensor_data_sen_time.setText(sensorInfo.getRecordTime());
                                sensor_data_sen_deviceid.setText(start.DeviceID);
                            }
                            else if(sensorInfo.getOperType() == 1 ){

                                sensor_data_one_all.setVisibility(View.VISIBLE);
                                sensor_data_null.setVisibility(View.GONE);
                                sensor_data_execute.setVisibility(View.VISIBLE);
                                sensor_data_one.setVisibility(View.GONE);
                                sensor_data_exe_switch.setVisibility(View.VISIBLE);

                                sensor_data_name.setText(sensorInfo.getName());
                                sensor_data_exe_switch.setChecked(Boolean.parseBoolean(sensorInfo.getValue()));
                                sensor_data_exe_type.setText("执行器");
                                sensor_data_exe_api.setText(sensorInfo.getApiTag());
                                sensor_data_exe_time.setText(sensorInfo.getRecordTime());
                                sensor_data_exe_deviceid.setText(start.DeviceID);

                                sensor_data_exe_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                                        Sensor_Activity.this.business.control(start.DeviceID, sensorInfo.getApiTag(),
                                                isChecked?1:0, new NCallBack<BaseResponseEntity>(getApplicationContext()) {
                                                    @Override
                                                    protected void onResponse(BaseResponseEntity response) {
                                                        if (response.getStatus() == 0){
                                                            Toast.makeText(Sensor_Activity.this,"执行成功",Toast.LENGTH_SHORT).show();
                                                        }
                                                        else{Toast.makeText(Sensor_Activity.this,"执行失败",Toast.LENGTH_SHORT).show();}
                                                    }
                                                });
                                    }
                                });
                            }

                            else if(sensorInfo.getOperType() == 2){
                                sensor_data_one_all.setVisibility(View.VISIBLE);
                                sensor_data_null.setVisibility(View.GONE);
                                sensor_data_execute.setVisibility(View.VISIBLE);
                                sensor_data_one.setVisibility(View.GONE);
                                sensor_data_exe_swt.setVisibility(View.VISIBLE);
                                sensor_data_exe_swt1.setVisibility(View.VISIBLE);
                                sensor_data_exe_swt2.setVisibility(View.VISIBLE);


                                sensor_data_name.setText(sensorInfo.getName());
                                sensor_data_exe_type.setText("执行器");
                                sensor_data_exe_api.setText(sensorInfo.getApiTag());
                                sensor_data_exe_time.setText(sensorInfo.getRecordTime());
                                sensor_data_exe_deviceid.setText(start.DeviceID);

                                sensor_data_exe_swt2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        business.control(start.DeviceID, sensorInfo.getApiTag(), 2,
                                                new NCallBack<BaseResponseEntity>(getApplicationContext()) {
                                                    @Override
                                                    protected void onResponse(BaseResponseEntity response) {
                                                    }
                                                });
                                    }
                                });

                                sensor_data_exe_swt1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        business.control(start.DeviceID, sensorInfo.getApiTag(), 1,
                                                new NCallBack<BaseResponseEntity>(getApplicationContext()) {
                                                    @Override
                                                    protected void onResponse(BaseResponseEntity response) {
                                                    }
                                                });
                                    }
                                });
                                sensor_data_exe_swt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        business.control(start.DeviceID, sensorInfo.getApiTag(), 0,
                                                new NCallBack<BaseResponseEntity>(getApplicationContext()) {
                                                    @Override
                                                    protected void onResponse(BaseResponseEntity response) {
                                                    }
                                                });
                                    }
                                });



                            }

                            else if (sensorInfo.getOperType() == 5){
                                sensor_data_led_name.setText(sensorInfo.getName());
                                sensor_data_led_type.setText(sensorInfo.getValue());
                                sensor_data_led_send.setVisibility(View.VISIBLE);
                                sensor_data_led.setVisibility(View.VISIBLE);

                                sensor_data_led_send.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String len_data = sensor_data_led_txt.getText().toString();
                                        Sensor_Activity.this.business.control(String.valueOf(sensorInfo.getDeviceID()), sensorInfo.getApiTag(),
                                                len_data, new NCallBack<BaseResponseEntity>(getApplicationContext()) {
                                            @Override
                                            protected void onResponse(BaseResponseEntity response) {
                                                if (response.getStatus() == 0){
                                                    Toast.makeText(Sensor_Activity.this,"成功执行",Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(Sensor_Activity.this,"执行失败",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        }
                        Log.i("~~~~~", "------------------------");
                    }
                });
            }
        }
    }
}
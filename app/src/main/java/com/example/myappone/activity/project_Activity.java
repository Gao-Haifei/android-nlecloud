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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myappone.R;
import com.example.myappone.tool.StatusBar;
import com.example.myappone.tool.start;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.newland.nle_sdk.responseEntity.DeviceInfo;
import cn.com.newland.nle_sdk.responseEntity.ProjectInfo;
import cn.com.newland.nle_sdk.responseEntity.SensorInfo;
import cn.com.newland.nle_sdk.responseEntity.base.BasePager;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;


public class project_Activity extends AppCompatActivity {

    public NetWorkBusiness business;

    project_list project_list;
    ListView list;

    Timer timer;


    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        timer = null;
        Log.i("TAG", "onStop: 线程消除");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        timer = new Timer();
        Task task = new Task();
        timer.schedule(task,500,5000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar statusBar = new StatusBar(project_Activity.this);
        statusBar.setColor(R.color.transparent);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.project_main);
        //初始化
        timer = new Timer();

        //初始化界面控件
//        EditText projects_id = findViewById(R.id.projects_id);
//        Button project_cx = findViewById(R.id.project_cx);
        list = findViewById(R.id.list);

        Task task = new Task();
        timer.schedule(task,500,5000);

        project_Activity.this.business = new NetWorkBusiness(start.TOKEN, start.URL);


    }

    class Task extends TimerTask{

        @Override
        public void run() {
            project_Activity.this.business.getProjects("", "", "", "", "2020-10-01", start.get_project_time(), "", new NCallBack<BaseResponseEntity<BasePager<ProjectInfo>>>(getApplicationContext()) {
                @Override
                protected void onResponse(BaseResponseEntity<BasePager<ProjectInfo>> response) {
                    List<ProjectInfo> pageSet = response.getResultObj().getPageSet();
                    List<Map<String, Object>> data = new ArrayList<>();
                    HashMap<String, Object> map;
                    for (int i = 0; i < pageSet.size(); i++) {
                        map = new HashMap<>();
                        map.put("name", pageSet.get(i).getName());
                        map.put("type", pageSet.get(i).getIndustry());
                        map.put("network", pageSet.get(i).getNetWorkKind());
                        map.put("id", pageSet.get(i).getProjectID());
                        map.put("apitag", pageSet.get(i).getProjectTag());
                        map.put("createtime", pageSet.get(i).getCreateDate());
                        data.add(map);
                    }
                    if (project_list == null) {
                        project_list = new project_list(project_Activity.this, data);
                        list.setAdapter(project_list);
                    } else {
                        project_list.Up_data(project_Activity.this, data);
                    }

                }
            });
        }
    }

    class project_list extends BaseAdapter {

        Context context;
        LayoutInflater layoutInflater;
        List<Map<String, Object>> data;

        public project_list(Context context, List<Map<String, Object>> data) {
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

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.project_item, null);
            }

            TextView project_name, project_type, project_id, project_apitag, project_createtime;
            TextView project_device, project_device_sensor, project_cl, project_history;

            ImageView project_lianjie, project_bianji, project_shanchu;
            LinearLayout project_project, btn_device;


            project_name = view.findViewById(R.id.project_name);
            project_type = view.findViewById(R.id.project_type);
            project_lianjie = view.findViewById(R.id.project_lianjie);
            project_bianji = view.findViewById(R.id.project_bianji);
            project_shanchu = view.findViewById(R.id.project_shanchu);
            project_id = view.findViewById(R.id.project_id);
            project_apitag = view.findViewById(R.id.project_apitag);
            project_createtime = view.findViewById(R.id.project_createtime);
            project_device = view.findViewById(R.id.project_device);
            project_device_sensor = view.findViewById(R.id.project_device_sensor);
            project_cl = view.findViewById(R.id.project_cl);
            project_history = view.findViewById(R.id.project_history);

            project_project = view.findViewById(R.id.project_project);
            btn_device = view.findViewById(R.id.btn_device);


            project_name.setText(data.get(i).get("name").toString());
            project_type.setText(data.get(i).get("type").toString());
            switch (data.get(i).get("network").toString()) {
                case "WIFI":
                    project_lianjie.setBackgroundResource(R.drawable.wiiii);
                    break;
                case "以太网":
                    project_lianjie.setBackgroundResource(R.drawable.ytw);
                    break;
                case "蜂窝网络(2G/3G/4G)":
                    project_lianjie.setBackgroundResource(R.drawable.fw);
                    break;
                case "蓝牙":
                    project_lianjie.setBackgroundResource(R.drawable.ly);
                    break;
            }
            project_id.setText("项目ID：" + data.get(i).get("id"));
            project_apitag.setText("项目标识码：" + data.get(i).get("apitag").toString());
            project_createtime.setText("创建时间：" + data.get(i).get("createtime").toString());

            project_Activity.this.business.getDeviceFuzzy("", "", "", "", "", data.get(i).get("id").toString(), "", "2020-10-01", start.get_project_time(), "",
                    new NCallBack<BaseResponseEntity<BasePager<DeviceInfo>>>(getApplicationContext()) {
                        @Override
                        protected void onResponse(BaseResponseEntity<BasePager<DeviceInfo>> response) {
                            List<DeviceInfo> pageSet = response.getResultObj().getPageSet();

                            if (pageSet.size() != 0) {
                                project_device.setText(pageSet.size() + "个设备");
                            }
                            else project_device.setText("0个设备");
                        }
                    });

            project_Activity.this.business.getAllSensors(data.get(i).get("id").toString(), new NCallBack<BaseResponseEntity<List<SensorInfo>>>(getApplicationContext()) {
                @Override
                protected void onResponse(BaseResponseEntity<List<SensorInfo>> response) {
                    project_device_sensor.setText(response.getResultObj().size()+"个传感器");
                }
            });


            project_project.setVisibility(View.VISIBLE);

            btn_device.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    start.ProjectId = data.get(i).get("id").toString();
                    Intent intent = new Intent(project_Activity.this, devices_Activity.class);
                    startActivity(intent);
                }
            });

            return view;
        }
    }

}
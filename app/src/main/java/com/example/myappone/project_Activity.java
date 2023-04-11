package com.example.myappone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.newland.nle_sdk.responseEntity.SensorInfo;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;


public class project_Activity extends AppCompatActivity {

    public NetWorkBusiness business;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.project_main);

        EditText project_id = findViewById(R.id.project_id);
        Button project_cx = findViewById(R.id.project_cx);

        ListView list =findViewById(R.id.project_list);
        //初始化界面控件

        project_Activity.this.business = new NetWorkBusiness(start.TOKEN,start.URL);
        //初始化
        project_cx.setOnClickListener(new View.OnClickListener() {
            //点击事件
            @Override
            public void onClick(View v) {
                start.ProjectId = project_id.getText().toString();
                //获取输入的项目ID
                project_Activity.this.business.getAllSensors(start.ProjectId,
                        new NCallBack<BaseResponseEntity<List<SensorInfo>>>(getApplicationContext()) {

                            @Override
                            protected void onResponse(BaseResponseEntity<List<SensorInfo>> response) {
                                   List<SensorInfo> Infos = response.getResultObj();
                                //获取列表信息
                                List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();//创建一个哈希表
                                Map<String, Object> map = new HashMap<String, Object>();//创建一个哈希表
                                map.put("name","全部传感器数据");
                                map.put("api","All_Sensor");
                                map.put("time","- - - - - - - -");
                                map.put("deviceid","999999");
                                data.add(map);
                                map = new HashMap<String, Object>();
                                //将数据放入哈希表

                                //遍历输出表中数据
                                for (SensorInfo item : Infos){
                                    map.put("name",item.getName());
                                    map.put("api",item.getApiTag());
                                    map.put("time",item.getCreateDate());
                                    map.put("deviceid",item.getDeviceID());
//                                    Log.i("~~~~~",item.getName()+"~~~"+item.getOperType());
                                    data.add(map);
                                    map = new HashMap<String, Object>();
                                    //Log.i("DeviceID", String.valueOf(item.getDataType()+"~~~"+item.getName()+"~~~"+item.getApiTag()));
                                }
                                //adapter进行数据交互
                                project_item adapter = new project_item(project_Activity.this, data);
                                list.setAdapter(adapter);

                            }

                });
            }
        });
    }

    public final static int LIGHT = 0;
    public final static int TEMP = 1;
    public final static int OTHER = -1;
    TextView sensor_data_sen_value;
    class myHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String value;
            Double v;
            switch (what){
                case LIGHT:
                    value =msg.getData().getString("LIGHT");
            }
        }
    }
}
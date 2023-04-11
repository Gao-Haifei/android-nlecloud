package com.example.myappone;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class project_item extends BaseAdapter{

    List<Map<String,Object>> data;
    public Context context;
    LayoutInflater layoutInflater;

    public project_item(Context context,List<Map<String,Object>>data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }
    public Context getContext(){return context;}

    @Override
    public int getCount() { return data.size(); }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_project_item,null);
        }
        LinearLayout list_item = convertView.findViewById(R.id.list_item);
        TextView project_item_name = convertView.findViewById(R.id.project_item_name);
        TextView project_item_time = convertView.findViewById(R.id.project_item_time);
        TextView project_item_api = convertView.findViewById(R.id.project_item_api);
        TextView project_item_deviceid = convertView.findViewById(R.id.project_item_deviceid);

        list_item.setMinimumHeight(200);
        Map<String,Object> item =data.get(position);


        project_item_name.setText(item.get("name").toString());
        project_item_api.setText(item.get("api").toString());
        project_item_time.setText(item.get("time").toString());
        project_item_deviceid.setText(item.get("deviceid").toString());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> sensor;
                sensor = data.get(position);
                start.ApiTag = sensor.get("api").toString();
                start.DeviceID = sensor.get("deviceid").toString();

                Intent intent = new Intent();
                intent.setClass(project_item.this.getContext(), Sensor_Activity.class);
                project_item.this.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

}
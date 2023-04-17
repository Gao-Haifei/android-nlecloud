package com.example.myappone.list_item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.myappone.R;
import com.example.myappone.tool.start;

import java.util.List;
import java.util.Map;

import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;


public class sensor_list extends BaseAdapter {

    List<Map<String,Object>> data;
    Context context;
    LayoutInflater layoutInflater;

    NetWorkBusiness business;

    public sensor_list(Context context,List<Map<String,Object>> data){
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    public void UpData(Context context,List<Map<String,Object>> data){
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

        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.data,null);
        }

        LinearLayout one = view.findViewById(R.id.one);
        LinearLayout two = view.findViewById(R.id.two);

        TextView sensor_name = view.findViewById(R.id.sensor_name);
        TextView tv_rgb_name = view.findViewById(R.id.tv_rgb_name);

        TextView sensor_apitag = view.findViewById(R.id.sensor_apitag);
        TextView tv_rgb_apitag = view.findViewById(R.id.tv_rgb_apitag);

        TextView sensor_value = view.findViewById(R.id.sensor_value);
        EditText tv_rgb_color = view.findViewById(R.id.tv_rgb_color);
        TextView send_color = view.findViewById(R.id.send_color);

        ImageButton relay_switch = view.findViewById(R.id.relay_switch);
        TextView sensor_time = view.findViewById(R.id.sensor_time);



        if (Long.parseLong(String.valueOf(data.get(i).get("type")))==0){

            one.setVisibility(View.VISIBLE);
            two.setVisibility(View.GONE);
            relay_switch.setVisibility(View.GONE);
            sensor_value.setVisibility(View.VISIBLE);
            one.setBackgroundResource(R.drawable.data_view);

            sensor_name.setText(data.get(i).get("name").toString());
            sensor_apitag.setText("ApiTag："+data.get(i).get("apitag"));
            sensor_value.setText("Value："+data.get(i).get("value"));
            sensor_time.setText(data.get(i).get("time").toString());

        } else if (Long.parseLong(String.valueOf(data.get(i).get("type")))==1) {
            one.setVisibility(View.VISIBLE);
            two.setVisibility(View.GONE);
            relay_switch.setVisibility(View.VISIBLE);
            sensor_value.setVisibility(View.GONE);
            one.setBackgroundResource(R.drawable.data_view2);

            sensor_name.setText(data.get(i).get("name").toString());
            sensor_apitag.setText("ApiTag："+data.get(i).get("apitag"));
            sensor_value.setText("");
            sensor_time.setText(data.get(i).get("time").toString());

            int value = Double.valueOf(data.get(i).get("value").toString()).intValue();
            if (value == 0){
                relay_switch.setBackgroundResource(R.drawable.close);
            } else if (value == 1) {
                relay_switch.setBackgroundResource(R.drawable.open);
            }

            relay_switch.setOnClickListener(view1 -> {
                sensor_list.this.business = new NetWorkBusiness(start.TOKEN, start.URL);
                if (value == 0){
                    sensor_list.this.business.control(start.DeviceID, data.get(i).get("apitag").toString(), 1,
                            new NCallBack<BaseResponseEntity>(context.getApplicationContext()) {
                                @Override
                                protected void onResponse(BaseResponseEntity response) {
                                    relay_switch.setBackgroundResource(R.drawable.open);
                                }
                            });
                } else if (value == 1) {
                    sensor_list.this.business.control(start.DeviceID, data.get(i).get("apitag").toString(), 0,
                            new NCallBack<BaseResponseEntity>(context.getApplicationContext()) {
                                @Override
                                protected void onResponse(BaseResponseEntity response) {
                                    relay_switch.setBackgroundResource(R.drawable.close);
                                }
                            });
                }
            });

        } else if (Long.parseLong(String.valueOf(data.get(i).get("type")))==5) {
            one.setVisibility(View.GONE);
            two.setVisibility(View.VISIBLE);
            relay_switch.setVisibility(View.GONE);
            sensor_value.setVisibility(View.GONE);

            if (data.get(i).get("apitag").equals("LCD_Show")){
                tv_rgb_color.setInputType(View.TEXT_ALIGNMENT_CENTER);
            }


            tv_rgb_name.setText(data.get(i).get("name").toString());
            tv_rgb_apitag.setText(data.get(i).get("apitag").toString());



            send_color.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tv_rgb_color.getText() == null || tv_rgb_color.getText().toString().equals("")){
                        return;
                    }
                    sensor_list.this.business = new NetWorkBusiness(start.TOKEN, start.URL);
                    int a = Integer.parseInt(tv_rgb_color.getText().toString());
                    if (a>255){
                        sensor_list.this.business.control(start.DeviceID, String.valueOf(data.get(i).get("apitag")),
                                255, new NCallBack<BaseResponseEntity>(context.getApplicationContext()) {
                                    @Override
                                    protected void onResponse(BaseResponseEntity response) {
                                        Log.i("TAG", "onResponse: 控制成功");
                                        tv_rgb_color.setText("");
                                    }
                                });
                    }
                    else {
                        sensor_list.this.business.control(start.DeviceID, String.valueOf(data.get(i).get("apitag")),
                                a, new NCallBack<BaseResponseEntity>(context.getApplicationContext()) {
                                    @Override
                                    protected void onResponse(BaseResponseEntity response) {
                                        Log.i("TAG", "onResponse: 控制成功");
                                        tv_rgb_color.setText("");
                                    }
                                });
                    }
                    tv_rgb_color.clearFocus();

                }
            });

        }

        return view;
    }
}

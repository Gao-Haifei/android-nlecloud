package com.example.myappone;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;

public class List_item extends BaseAdapter{

    List<Map<String,Object>> data;
    public Context context;
    LayoutInflater layoutInflater;


    NetWorkBusiness business;
    public List_item(Context context, List<Map<String,Object>>data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    //拿到列表的长度
    public Context getContext(){return context;}

    @Override
    public int getCount() {
        return data.size();
    }

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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_item,null);
        }

        List_item.this.business = new NetWorkBusiness(start.TOKEN,start.URL);
        //初始化

        TextView name = convertView.findViewById(R.id.item_name);
        TextView state = convertView.findViewById(R.id.item_text);
        Switch switc = convertView.findViewById(R.id.item_switch);
        TextView type = convertView.findViewById(R.id.item_type);
        TextView api = convertView.findViewById(R.id.item_api);
        TextView time = convertView.findViewById(R.id.item_time);
        TextView deviceid = convertView.findViewById(R.id.item_deviceid);
        //初始化控件
        Map<String,Object> item = data.get(position);

        if((int)item.get("type") == 2){
            switc.setVisibility(View.VISIBLE);//展示
            state.setVisibility(View.GONE);//隐藏
            Log.i("switch",String.valueOf(item.get("price")));
            switc.setChecked(item.get("price").equals("true")?true:false);
            name.setText(item.get("name").toString());
            type.setText(item.get("type").toString());
            api.setText(item.get("api").toString());
            time.setText(item.get("time").toString());
            deviceid.setText(item.get("projectid").toString());
            switc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    List_item.this.business.control(item.get("projectid").toString(), item.get("api").toString(), isChecked ? "1" : "0",
                            new NCallBack<BaseResponseEntity>(getContext()) {
                                @Override
                                protected void onResponse(BaseResponseEntity response) {
                                    if(response.getStatus() == 0){
                                        Toast.makeText(context,"执行成功",Toast.LENGTH_SHORT).show();
                                    }else Toast.makeText(context,"执行失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

        }
        else{
            switc.setVisibility(View.GONE);
            state.setVisibility(View.VISIBLE);
            state.setText(item.get("price").toString());
            name.setText(item.get("name").toString());
            type.setText(item.get("type").toString());
            api.setText(item.get("api").toString());
            time.setText(item.get("time").toString());
            deviceid.setText(item.get("deviceid").toString());
        }
        return convertView;
    }
}
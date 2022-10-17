package com.dy.huibiao_f80.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;

import java.util.List;

/**
 * 　 ┏┓　  ┏┓+ +
 * 　┏┛┻━━ ━┛┻┓ + +
 * 　┃　　　　 ┃
 * 　┃　　　　 ┃  ++ + + +
 * 　┃████━████+
 * 　┃　　　　 ┃ +
 * 　┃　　┻　  ┃
 * 　┃　　　　 ┃ + +
 * 　┗━┓　  ┏━┛
 * 　  ┃　　┃
 * 　  ┃　　┃　　 + + +
 * 　  ┃　　┃
 * 　  ┃　　┃ + 神兽保佑,代码无bug
 * 　  ┃　　┃
 * 　  ┃　　┃　　+
 * 　  ┃　 　┗━━━┓ + +
 * 　　┃ 　　　　 ┣┓
 * 　　┃ 　　　 ┏┛
 * 　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　 ┃┫┫ ┃┫┫
 * 　　 ┗┻┛ ┗┻┛+ + + +
 *
 * @author: wangzhenxiong
 * @data: 10/14/22 4:08 PM
 * Description:
 */
public class MySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    List<BaseProjectMessage>messageList;
    Context mContext;
    public MySpinnerAdapter(List<BaseProjectMessage>list, Context context) {
        messageList=list;
        mContext=context;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.spinner_item_layout, null);
        TextView tvgetView=(TextView) convertView.findViewById(R.id.name);
        BaseProjectMessage item = (BaseProjectMessage) getItem(position);
        tvgetView.setText(item.getCVName());


        return convertView;
    }

    /*@Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.getdropdowview, null);
        TextView tvdropdowview=(TextView) convertView.findViewById(R.id.tvgetdropdownview);
        tvdropdowview.setText(getItem(position).toString());
        return convertView;
    }*/

}

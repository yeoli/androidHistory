package com.example.yeol.jolup1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by demm on 2019-03-22.
 */

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListItem> list = new ArrayList<ListItem>();
    public ListViewAdapter() {
        super();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item,parent,false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.tv_name);
        TextView address = (TextView)convertView.findViewById(R.id.tv_address);

        ListItem item = list.get(position);

        name.setText(item.getBtname());
        address.setText(item.getBtMaddress());

        return convertView;
    }
    @Override
    public ListItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public boolean overlapcheck(String name){
        for(ListItem i : list){
            if(i.getBtname().equals(name)){
                return false;
            }
        }
        return true;
    }

    public void add(String name, String address){
        ListItem item = new ListItem();

        item.setBtname(name);
        item.setBtMaddress(address);

        list.add(item);

    }
}
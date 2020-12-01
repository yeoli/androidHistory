package com.example.yeol.jolup1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by yeol on 2019-05-09.
 */

public class CheckListAdapter extends BaseAdapter {

    private Context context;
    private List<CheckList> checkList;

    public CheckListAdapter(Context context, List<CheckList> checkList) {
        this.context = context;
        this.checkList = checkList;
    }



    @Override
    public int getCount() {
        return checkList.size();
    }

    @Override
    public Object getItem(int i) {
        return checkList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_item, null);
        TextView check_date = (TextView) v.findViewById(R.id.check_date);
        TextView check_time = (TextView) v.findViewById(R.id.check_time);

        check_date.setText(checkList.get(i).getCheck_date());
        check_time.setText(checkList.get(i).getCheck_time());

        return v;
    }
}

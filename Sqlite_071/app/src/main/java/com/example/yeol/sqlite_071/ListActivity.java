package com.example.yeol.sqlite_071;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeol on 2018-07-17.
 */

public class ListActivity extends Activity {

    ListView list;
    Button btnReturn;
    ArrayAdapter<String> adapter;
    ArrayList<String> arr;
    MYDBHelper helper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        list = (ListView) findViewById(R.id.listView1);
        btnReturn = (Button) findViewById(R.id.btnReturn);

        arr = new ArrayList<String>();

        helper = new MYDBHelper(this);
        select();
        //어댑터 생성
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,arr);
        list.setAdapter(adapter);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                 db = helper.getWritableDatabase();

                 db.execSQL("delete from mydb where name = '"+arr.get(i)+"';");
                 db.close();
                 arr.clear();
                 adapter.notifyDataSetChanged();
                 select();

                return false;
            }
        });

    }

    //조회 메서드를 만들기
    public void select()
    {
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("select id, name from mydb;",null);

        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            arr.add(name);
        }
    }
}

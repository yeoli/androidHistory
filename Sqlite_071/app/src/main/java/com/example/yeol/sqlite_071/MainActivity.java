package com.example.yeol.sqlite_071;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MYDBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    EditText edtName;
    Button btnList, btnSave;
    //TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = (EditText) findViewById(R.id.edtName);
        btnList = (Button) findViewById(R.id.btnList);
        btnSave = (Button) findViewById(R.id.btnSave);
        //tvTest = (TextView) findViewById(R.id.tvTest);

        //헬퍼 생성
        dbHelper = new MYDBHelper(this);

        //저장
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = dbHelper.getWritableDatabase();

                String saveName = edtName.getText().toString();

                String sql = "insert into mydb values (null, '" + saveName + "');";
                db.execSQL(sql);

            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db = dbHelper.getReadableDatabase();

                // cursor = db.rawQuery("select id, name from mydb;",null);

                // while (cursor.moveToNext()){
                //   int id = cursor.getInt(0);
                //   String name = cursor.getString(1);
                // tvTest.setText(name+"{"+id+"}");
                //}

                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });


        //목록
    }
}



class MYDBHelper extends SQLiteOpenHelper{

    private static final String DNAME = "dblist";
    private static final int DVERSION = 1;

    public MYDBHelper(Context context) {
        super(context, DNAME, null, DVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //테이블 생성
        db.execSQL("create table mydb (id integer primary key autoincrement,name text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //삭제 후 호출

        db.execSQL("drop table if exists mydb");
        onCreate(db);
    }
}


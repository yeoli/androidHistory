package com.example.yeol.sqlitehelpertest_062;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yeol on 2018-07-16.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    static String DBNAME = "myUniv";
    static int DBVERSION = 2;
    //생성자
    public MyDBHelper(Context context) {
        super(context, "univDb",null,2);
    }
    //테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table univ (id integer primary key, uName varchar2(20) primary key,"+" uUniv varchar(20));");


    }
    //테이블 삭제 후 다시 생성
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists univ");
        onCreate(db);

    }
}

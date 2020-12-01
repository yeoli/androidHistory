package com.example.yeol.sqlitehelpertest_062;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyDBHelper myDBHelper;
    EditText edtName, edtSchool;
    Button btnSearch, btnadd;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("학교를 찾아라");

        edtName = (EditText) findViewById(R.id.edtName);
        edtSchool = (EditText) findViewById(R.id.edtSchool);
        btnadd = (Button) findViewById(R.id.btnadd);
        btnSearch = (Button) findViewById(R.id.btnSearch);


        //헬퍼 인스턴스 생성
        myDBHelper = new MyDBHelper(this);


        //저장
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = myDBHelper.getWritableDatabase();
                db.execSQL("insert into univ VALUES (null,'"
                        +edtName.getText().toString()+"', "
                         +edtSchool.getText().toString()+");");
                db.close();

                Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show();

                //자판 내리기
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtSchool.getWindowToken(),0);

                edtName.setText("");
                edtSchool.setText("");

                btnadd.callOnClick();
            }
        });

        //검색
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = myDBHelper.getReadableDatabase();

                String Name = edtName.getText().toString();
                Cursor cursor;

                cursor = db.rawQuery("select uName, uUniv from univ where Name='"
                        +edtSchool.getText().toString()+"';",null);

                while (cursor.moveToNext()) {
                    String Uuniv = cursor.getString(1);
                    edtSchool.setText(Uuniv);
                }

                //출력

                cursor.close();
                db.close();
            }
        });

    }
}

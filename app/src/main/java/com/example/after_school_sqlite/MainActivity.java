package com.example.after_school_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MyDBHelper myHelper;
    EditText editName, editCount, editSelectName, editSelectCount;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("아이돌 그룹 관리 DB");

        editName = findViewById(R.id.edit_name);
        editCount = findViewById(R.id.edit_count);
        editSelectName = findViewById(R.id.edit_select_name);
        editSelectCount = findViewById(R.id.edit_select_count);

        Button btnInit = findViewById(R.id.btn_init);
        Button btnInput = findViewById(R.id.btn_input);
        Button btnSelect = findViewById(R.id.btn_select);

        myHelper = new MyDBHelper(this);
        btnInit.setOnClickListener(btnListener);
        btnInput.setOnClickListener(btnListener);
        btnSelect.setOnClickListener(btnListener);
    }
    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_init:
                    sqlDB = myHelper.getWritableDatabase(); //읽기와 쓰기가 모두 가능한 데이터베이스
                    myHelper.onUpgrade(sqlDB, 1, 2);//원래버전에서 새 버전으로 올려주면서 기존 테이블 삭제 !
                    sqlDB.close();
                    break;
                case R.id.btn_input:
                    sqlDB = myHelper.getWritableDatabase();
                    sqlDB.execSQL("insert into groupTBL values('" + editName.getText().toString() + "', " + editCount.getText().toString()+")");
                    sqlDB.close();
                    Toast.makeText(getApplicationContext(), "정상적으로 저장됨", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_select:
                    sqlDB = myHelper.getReadableDatabase();
                    Cursor cursor = sqlDB.rawQuery("select * from groupTBL;", null);
                    String strNames = "아이돌이름\r\n-----------\r\n";
                    String strNumbers = "인원수\r\n-----------\r\n";
                    while (cursor.moveToNext()) {//현재 커서가 전체 테이블을 가리키고 있다가 무브투넥스트를 만나면 원하는 행의 데이터 선택 가능
                        strNames += cursor.getString(0)+ "\r\n"; //처음에 선택된 행에 getString을 갖습니다.
                        strNumbers +=cursor.getInt(1)+"\r\n";
                    }
                    editSelectName.setText(strNames);
                    editSelectCount.setText(strNumbers);
                    cursor.close();
                    sqlDB.close();
                    break;
            }
        }
    };
}
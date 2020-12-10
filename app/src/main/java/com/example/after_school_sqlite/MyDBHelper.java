package com.example.after_school_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(@Nullable Context context) {
        super(context, "groupDB", null, 1);
    }
    //table 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table groupTBL(gName char(20) PRIMARY KEY, gNumber INTEEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists groupTBL"); // groupTBL이 있다면 삭제해라
        onCreate(db);// 그리구 다시 새롭게 생성
    }
}
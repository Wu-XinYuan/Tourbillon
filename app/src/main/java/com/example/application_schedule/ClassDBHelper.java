package com.example.application_schedule;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClassDBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "classes_db";

    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }


    public ClassDBHelper(@Nullable Context context,
                         @Nullable String name,
                         @Nullable SQLiteDatabase.CursorFactory factory,
                         int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +
                "(c_id varchar(50)," +
                " c_name varchar(50) not null," +
                " c_time Integer," +
                " c_duration Integer," + //持续几节课
                " c_startWeek Integer," +
                " c_endWeek Integer," +
                " c_day Integer," +
                " c_room varchar(50)," +
                " c_teacher varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
package com.example.Tourbillon;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClassDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=7;
    public static final String TABLE_NAME = "classes_db";
    public ClassDBHelper(Context context ) { super(context, TABLE_NAME, null, DATABASE_VERSION);}

    /**
     * 创建一个数据库，建表，只在第一次时调用
     */
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
                " c_teacher varchar(50),"+
                " c_detail varchar(50)," +
                " c_weekCode varchar(50)," +
                " c_isClass Boolean)");
    }

    /**
     * 作用：更新数据库表结构
     * 调用时机：数据库版本发生变化的时候回调（取决于数据库版本）
     * 创建SQLiteOpenHelper子类对象的时候,必须传入一个version参数
     //该参数就是当前数据库版本, 只要这个版本高于之前的版本, 就会触发这个onUpgrade()方法
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
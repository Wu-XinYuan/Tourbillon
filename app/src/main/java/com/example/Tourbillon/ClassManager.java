package com.example.Tourbillon;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ClassManager {
    private ClassDBHelper dbHelper;
    public ClassManager(Context context) {
        dbHelper = new ClassDBHelper(context);
    }
    private final String[] pleasantColors = {"#9FFC6472", "#9FF4B2A6", "#9FECCDB3", "#9FBCEFD0", "#9FA1E8E4", "#9F23C8B2", "#9FC3ACEE"};

    /**
     * 插入数据
     */
    public void insertData(String id, String name, Integer time, Integer duration, Integer startWeek, Integer endWeek, Integer day, String room, String teacher) {
        // 实际上是更新，为了保存顺序
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.i(TAG, "insertData" + id + name);
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_id", id);
        contentValues.put("c_name", name);
        contentValues.put("c_time", time);
        contentValues.put("c_duration", duration);
        contentValues.put("c_startWeek", startWeek);
        contentValues.put("c_endWeek", endWeek);
        contentValues.put("c_day", day);
        contentValues.put("c_room", room);
        contentValues.put("c_teacher", teacher);
        long r = db.insert(ClassDBHelper.TABLE_NAME, null, contentValues);
        db.close();
    }

    public boolean insert(Class_t myclass) {
        //与数据库建立连接
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_id", myclass.getC_id());
        contentValues.put("c_name", myclass.getC_name());
        contentValues.put("c_time", myclass.getC_time());
        contentValues.put("c_duration", myclass.getC_duration());
        contentValues.put("c_startWeek", myclass.getC_startWeek());
        contentValues.put("c_endWeek", myclass.getC_endWeek());
        contentValues.put("c_day", myclass.getC_day());
        contentValues.put("c_room", myclass.getC_room());
        contentValues.put("c_teacher", myclass.getC_teacher());
        //插入每一行数据
        long r = db.insert(ClassDBHelper.TABLE_NAME, null, contentValues);
        db.close();
        if (r != -1)
            return true;
        else
            return false;
    }

    /**
     * 删除数据
     */
    public void delete(String id) {
        //与数据库建立连接
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ClassDBHelper.TABLE_NAME, "c_id" + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * 通过id查找，返回一个Class_t对象
     */
    public Class_t getClassById(String id) {
        //与数据库建立连接
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = " select " + Class_t.KEY_id + "," + Class_t.COLUMN_name +
                "," + Class_t.COLUMN_time + "," + Class_t.COLUMN_duration +
                "," + Class_t.COLUMN_startw + "," + Class_t.COLUMN_endw +
                "," + Class_t.COLUMN_day + "," + Class_t.COLUMN_room + "," + Class_t.COLUMN_teacher +
                " from " + ClassDBHelper.TABLE_NAME + " where " + Class_t.KEY_id + "=?";
        Class_t myclass = new Class_t();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex(Class_t.KEY_id);
            int j = cursor.getColumnIndex(Class_t.COLUMN_name);
            int k = cursor.getColumnIndex(Class_t.COLUMN_time);
            int l = cursor.getColumnIndex(Class_t.COLUMN_duration);
            int p = cursor.getColumnIndex(Class_t.COLUMN_startw);
            int q = cursor.getColumnIndex(Class_t.COLUMN_endw);
            int t = cursor.getColumnIndex(Class_t.COLUMN_day);
            int m = cursor.getColumnIndex(Class_t.COLUMN_room);
            int n = cursor.getColumnIndex(Class_t.COLUMN_teacher);
            if(i >= 0)
                myclass.c_id = cursor.getString(i);
            if(j >= 0)
                myclass.c_name = cursor.getString(j);
            if(k >= 0)
                myclass.c_time = cursor.getInt(k);
            if(l >= 0)
                myclass.c_duration = cursor.getInt(l);
            if(p >= 0)
                myclass.c_startWeek = cursor.getInt(p);
            if(q >= 0)
                myclass.c_endWeek = cursor.getInt(q);
            if(t >= 0)
                myclass.c_day = cursor.getInt(t);
            if(m >= 0)
                myclass.c_room = cursor.getString(m);
            if(n >= 0)
                myclass.c_teacher = cursor.getString(n);
        }
        cursor.close();
        db.close();
        return myclass;
    }

    @SuppressLint("Range")
    public List<Class_t> query() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = " select " + Class_t.KEY_id + "," + Class_t.COLUMN_name +
                "," + Class_t.COLUMN_time + "," + Class_t.COLUMN_duration +
                "," + Class_t.COLUMN_startw + "," + Class_t.COLUMN_endw +
                "," + Class_t.COLUMN_day + "," + Class_t.COLUMN_room + "," + Class_t.COLUMN_teacher +
                " from " + ClassDBHelper.TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, null);
        List<Class_t> classes = new ArrayList<>();

        // 将游标移到开头
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) { // 游标只要不是在最后一行之后，就一直循环
            Log.i(TAG, "query: ");
            if (!cursor.getString(cursor.getColumnIndex("c_day")).equals("0")) {
                Class_t Class = new Class_t();

                Class.setC_id(cursor.getString(cursor.getColumnIndex("c_id")));
                Class.setC_name(cursor.getString(cursor.getColumnIndex("c_name")));
                Class.setC_time(Integer.parseInt(cursor.getString(cursor.getColumnIndex("c_time"))));
                Class.setC_startWeek(Integer.parseInt(cursor.getString(cursor.getColumnIndex("c_startWeek"))));
                Class.setC_endWeek(Integer.parseInt(cursor.getString(cursor.getColumnIndex("c_endWeek"))));
                Class.setC_duration(Integer.parseInt(cursor.getString(cursor.getColumnIndex("c_duration"))));
                Class.setC_day(Integer.parseInt(cursor.getString(cursor.getColumnIndex("c_day"))));
                Class.setC_room(cursor.getString(cursor.getColumnIndex("c_room")));
                Class.setC_teacher(cursor.getString(cursor.getColumnIndex("c_teacher")));
                Random random = new Random();
                Class.setColor(Color.parseColor(pleasantColors[random.nextInt(5)]));
                classes.add(Class);
            }
            // 将游标移到下一行
            cursor.moveToNext();

        }

        cursor.close();
        return classes;
    }

    /**
     * 更新数据
     */
    public void update(Class_t myclass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("c_id", myclass.getC_id());
        contentValues.put("c_name", myclass.getC_name());
        contentValues.put("c_time", myclass.getC_time());
        contentValues.put("c_duration", myclass.getC_duration());
        contentValues.put("c_startWeek", myclass.getC_startWeek());
        contentValues.put("c_endWeek", myclass.getC_endWeek());
        contentValues.put("c_day", myclass.getC_day());
        contentValues.put("c_room", myclass.getC_room());
        contentValues.put("c_teacher", myclass.getC_teacher());

        db.update(ClassDBHelper.TABLE_NAME, contentValues, Class_t.KEY_id + "=?", new String[]{String.valueOf(myclass.c_id)});
        db.close();
    }
}


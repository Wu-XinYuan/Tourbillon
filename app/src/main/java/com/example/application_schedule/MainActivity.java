package com.example.application_schedule;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    ClassDBHelper classDBHelper; //课程数据库 dbhelper
    private SQLiteDatabase classDatabase; //课程表数据库
    private final String[] pleasantColors = {"#9FFC6472", "#9FF4B2A6", "#9FECCDB3", "#9FBCEFD0", "#9FA1E8E4", "#9F23C8B2", "#9FC3ACEE"};
    Date curDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //填写日期
        TextView textView_date = findViewById(R.id.TextView_date);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        curDate = new Date(System.currentTimeMillis());
        textView_date.setText(formatter.format(curDate));

        //创建课程数据库
        classDBHelper = new ClassDBHelper(MainActivity.this, null, null, 1);
        classDatabase = classDBHelper.getWritableDatabase();

        //测试：加入测试数据
        addTestData();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged: ");
        drawClassBoxes();
        TextView view_weekHeader = findViewById(R.id.textView_WeekHeader);
        ScheduleView scheduleView = findViewById(R.id.ScheduleView);
        view_weekHeader.setWidth(scheduleView.getWidth());
        Log.d(TAG, "onWindowFocusChanged: header width:"+view_weekHeader.getWidth());
        //设置初始滚动位置
        ScrollView scrollView = findViewById(R.id.scrollView_main);
        scrollView.scrollTo(0, 1440);
    }


    private void insertData(String id, String name, Integer time, Integer duration, Integer startWeek, Integer endWeek, Integer day, String room, String teacher) {
        // 实际上是更新，为了保存顺序
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
        long r = classDatabase.insert(ClassDBHelper.TABLE_NAME, null, contentValues);
    }

    private void insertData(String id, String name, Integer time, Integer day) {
        insertData(id, name, time, 2, 1, 16, day, " ", " ");
    }

    private void addTestData() {
        // 加入测试课程
        insertData("Gaoshu", "高数", 3, 1);
        insertData("RuanGong", "软件工程", 1, 2);
        insertData("RuanGong2", "软件工程", 3, 2);
        insertData("ShuJuKu", "数据库", 5, 3, 1, 16, 3, "东上院101", "x老师");
        insertData("KC4", "课程4", 3, 2, 1, 8, 4, "东上院101", "x老师");
        insertData("KC5", "课程5", 10, 3, 1, 16, 5, "东上院101", "x老师");
        insertData("KC6", "课程6", 8, 2, 1, 16, 6, "东上院101", "x老师");
        //insertData(6, "课程7", 1, 2, 1, 16, 7, "东上院102", "x老师");
        printClassDatabase();
    }

    public void drawClassBoxes() {

        //从数据库拿到课程数据保存在链表
        List<Class_t> classes = query();
        ScheduleView scheduleView = findViewById(R.id.ScheduleView);
        scheduleView.setEvents(classes);
    }

    @SuppressLint("Range")
    public List<Class_t> query() {

        List<Class_t> classes = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = classDatabase.query(ClassDBHelper.TABLE_NAME, null, null, null, null, null, null);

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

    public void printClassDatabase() {
        List<Class_t> classes = query();
        for (Class_t c : classes) {
            Log.i(TAG, "printClassDatabase: " + c.toString());
        }
    }
}
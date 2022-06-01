package com.example.Tourbillon;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Arrays;

public class ScheduleActivity extends AppCompatActivity {
    final ClassManager classOp = new ClassManager(ScheduleActivity.this);
    public static final int ACTION_INSERT = 0;
    public static final int ACTION_MODIFY = 1;
    public static final int ACTION_DETAIL = 2;
    private final boolean[] isWeekSelected = new boolean[Class_t.MAX_WEEKS];
    private final String[] weekItems = new String[Class_t.MAX_WEEKS];
    private final String[] startItems = new String[Class_t.MAX_STEPS];
    private EditText etSchedule, etDetail;
    private TextView tvWeeks, tvDay, tvStart, tvEnd;
    private int day;
    private int start;
    private int end;
    private int step;
    private String[] dayItems;
    private String[] endItems;
    private char[] weekCode = "000000000000000000".toCharArray();
    private int action;
    private Class_t course;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initUI();
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Schedule");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> ret());

        etSchedule = findViewById(R.id.et_schedule);
        etDetail = findViewById(R.id.et_detail);
        tvWeeks = findViewById(R.id.tv_weeks);
        tvDay = findViewById(R.id.tv_day);
        tvDay.setText(getResources().getStringArray(R.array.days_in_week)[day]);
        tvDay.setOnClickListener(v -> showDayDialog());
        tvStart = findViewById(R.id.tv_start);
        //这里到时候可以改成DatePickerDialog选择的结果
        tvStart.setText(String.format(getString(R.string.period), String.valueOf(start)));
        tvStart.setOnClickListener(v -> showStartDialog());
        tvEnd = findViewById(R.id.tv_end);
        //同DatePickerDialog
        tvEnd.setText(String.format(getString(R.string.period), String.valueOf(end)));
        tvEnd.setOnClickListener(v -> showEndDialog());
        tvWeeks.setOnClickListener(v -> showWeekDialog());

        Intent intent = getIntent();
        action = intent.getIntExtra("action", ACTION_INSERT);
        if (action == ACTION_INSERT) {
            day = 1;
            int week = 1;
            end = start = 1;
            step = 1;
            isWeekSelected[week - 1] = true;
            course = new Class_t();
            weekCode[0]='1';
        } else if (action == ACTION_DETAIL) {
            day = intent.getIntExtra("day", 1);
            start = intent.getIntExtra("time", 1);
            int week = intent.getIntExtra("startweek", 1);
            course = ClassManager.query(week,day,start);
        } else if (action == ACTION_MODIFY){
            day = intent.getIntExtra("day", 1);
            start = intent.getIntExtra("time", 1);
            int week = intent.getIntExtra("startweek", 1);
            course = ClassManager.query(week,day,start);
            ClassManager.delete(course);
        }
        actionChange();
        refreshTextViewAfterDialog();
    }

    private void loadInitData() {
        if (action != ACTION_INSERT) {
            etSchedule.setText(course.c_name);
            etDetail.setText(course.c_detail);
            day = course.c_day;
            start = course.c_time;
            step = course.c_duration;
            end = start + step - 1;
            weekCode = course.getWeekCode().toCharArray();
            for (int i = 0; i < Class_t.MAX_WEEKS; i++) {
                isWeekSelected[i] = weekCode[i] == '1';
            }
        }
        else {
            for (int i = 0; i < Class_t.MAX_WEEKS; i++)
                weekCode[i]=(isWeekSelected[i])?'1':'0';
        }

        for (int i = 1; i <= Class_t.MAX_WEEKS; i++) {
            weekItems[i - 1] = String.format(getString(R.string.week), String.valueOf(i));
        }
        dayItems = getResources().getStringArray(R.array.days_in_week);
        for (int i = 1; i <= Class_t.MAX_STEPS; i++)
            startItems[i - 1] = String.format(getString(R.string.period), String.valueOf(i));
        endItems = new String[Class_t.MAX_STEPS - start + 1];
        for (int i = start; i <= Class_t.MAX_STEPS; i++)
            endItems[i - start] = String.format(getString(R.string.period), String.valueOf(i));

        tvStart.setText(startItems[start - 1]);
        tvDay.setText(dayItems[day - 1]);
        tvEnd.setText(endItems[end - start]);
    }

    private void refreshTextViewAfterDialog() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < isWeekSelected.length; i++) {
            if (isWeekSelected[i]) {
                stringBuilder.append(i + 1);
                stringBuilder.append(" ");
            }
        }
        tvWeeks.setText(stringBuilder.toString().trim());
    }

    private void showWeekDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.select_week));
        builder.setMultiChoiceItems(weekItems, isWeekSelected, (dialog, which, isChecked) -> {
            isWeekSelected[which] = isChecked;
            weekCode[which] = isChecked ? '1' : '0';
        });
        builder.setNeutralButton(R.string.ok,
                (dialog, which) -> refreshTextViewAfterDialog());
        builder.setNegativeButton(getString(R.string.select_all), (dialog, which) -> {
            Arrays.fill(isWeekSelected, true);
            Arrays.fill(weekCode, '1');
            refreshTextViewAfterDialog();
        });
        builder.setPositiveButton(getString(R.string.unselect_all), (dialog, which) -> {
            Arrays.fill(isWeekSelected, false);
            Arrays.fill(weekCode, '0');
            refreshTextViewAfterDialog();
        });
        builder.show();
    }

    private void showStartDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.select_start));
        builder.setSingleChoiceItems(startItems, start - 1,
                (dialog, which) -> start = which + 1);
        builder.setPositiveButton(R.string.ok,
                (dialog, which) -> {
                    tvStart.setText(startItems[start - 1]);
                    endItems = new String[Class_t.MAX_STEPS - start + 1];
                    for (int i = start; i <= Class_t.MAX_STEPS; i++)
                        endItems[i - start] = String.format(getString(R.string.period), String.valueOf(i));
                    if (step - 1 < Class_t.MAX_STEPS - start)
                        tvEnd.setText(endItems[step - 1]);
                    else
                        tvEnd.setText(endItems[0]);
                    end = start + step - 1;
                });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    private void showDayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.select_day));
        builder.setSingleChoiceItems(dayItems, day - 1,
                (dialog, which) -> day = which + 1);
        builder.setPositiveButton(R.string.ok,
                (dialog, which) -> tvDay.setText(dayItems[day - 1]));
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    private void showEndDialog() {
        endItems = new String[Class_t.MAX_STEPS - start + 1];
        for (int i = start; i <= Class_t.MAX_STEPS; i++)
            endItems[i - start] = String.format(getString(R.string.period), String.valueOf(i));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.select_end));
        builder.setSingleChoiceItems(endItems, step - 1,
                (dialog, which) -> {
                    end = which + start;
                    step = end - start + 1;
                });
        builder.setPositiveButton(R.string.ok,
                (dialog, which) -> tvEnd.setText(endItems[step - 1]));
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }


    private boolean checkCourse(boolean insert) {
        if ("".equals(etSchedule.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.please_input_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(tvWeeks.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.please_select_weeks), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (end < start) {
            Toast.makeText(this, getString(R.string.please_select_period), Toast.LENGTH_SHORT).show();
            return false;
        }
        course.setC_name(etSchedule.getText().toString().trim());
        course.setC_time(start);
        course.setC_duration(step);
        course.setC_day(day);
        course.setC_isClass(false);
        course.setC_detail(etDetail.getText().toString().trim());
        course.setWeekCode(new String(weekCode));
        int startWeek = 0, endWeek=17;
        while (startWeek<18 && weekCode[startWeek]=='0')
            startWeek++;
        while (endWeek>=0 && weekCode[endWeek]=='0')
            endWeek--;
        course.setC_startWeek(startWeek+1);
        course.setC_endWeek(endWeek+1);
        course.setC_isClass(false);
        return ClassManager.insert(course);
    }


    private void deleteCourse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.warning))
                .setMessage(String.format(getString(R.string.sure_to_delete), course.getC_name()))
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    classOp.delete(course);
                    finish();
                })
                .setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.schedule_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_apply).setVisible(action != ACTION_DETAIL);
        menu.findItem(R.id.action_modify).setVisible(action == ACTION_DETAIL);
        menu.findItem(R.id.action_delete).setVisible(action == ACTION_MODIFY);
        menu.findItem(R.id.action_cancel).setVisible(action == ACTION_MODIFY);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_apply) {
            if (action == ACTION_INSERT || action == ACTION_MODIFY) {
                if (checkCourse(action == ACTION_INSERT))
                    finish();
            }
        } else if (item.getItemId() == R.id.action_cancel) {
            if (action==ACTION_MODIFY)
                ClassManager.insert(course);
            action = ACTION_DETAIL;
            invalidateOptionsMenu();
            actionChange();
        } else if (item.getItemId() == R.id.action_delete) {
            deleteCourse();
        } else if (item.getItemId() == R.id.action_modify) {
            action = ACTION_MODIFY;
            invalidateOptionsMenu();
            actionChange();
        }
        return true;
    }

    private void actionChange() {
        etSchedule.setEnabled(action != ACTION_DETAIL);
        etDetail.setEnabled(action != ACTION_DETAIL);
        tvWeeks.setEnabled(action != ACTION_DETAIL);
        tvEnd.setEnabled(action != ACTION_DETAIL);
        tvStart.setEnabled(action != ACTION_DETAIL);
        tvDay.setEnabled(action != ACTION_DETAIL);

        loadInitData();
    }

    private void ret(){
        if (action == ACTION_MODIFY)
            ClassManager.insert(course);
        finish();
    }
}
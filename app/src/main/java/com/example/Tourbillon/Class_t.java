package com.example.Tourbillon;
import com.example.Tourbillon.R;

public class Class_t {
    private String c_id;
    private String c_name;
    private int c_time;   //1-12
    private int c_duration;
    private int c_startWeek;
    private int c_endWeek;
    private int c_day;    //1-7
    private String c_room;
    private String c_teacher;
    private int contentColor;
    private String[] start_times = {"8:00", "8:55", "10:00", "10:55", "12:55", "14:00", "14:55", "16:00", "16:55", "18:00", "18:55", "19:45"};
    private String [] end_times = {"8:45", "9:40", "10:45", "11:40", "13:40", "14:45", "15:40", "16:45", "17:40", "18:45", "19:40", "20:40"};


    @Override
    public String toString() {
        return "Classes{" +
                "c_id=" + c_id +
                ", c_name='" + c_name + '\'' +
                ", c_time='" + c_time + '\'' +
                ", c_duration='" + c_duration + '\'' +
                ", c_startWeek='" + c_startWeek + '\'' +
                ", c_endWeek='" + c_endWeek + '\'' +
                ", c_day='" + c_day + '\'' +
                ", c_room='" + c_room + '\'' +
                ", c_teacher='" + c_teacher + '\'' +
                '}';
    }

    public String getDisplayText() {
        return c_name + '\n' + c_room + '\n' + c_teacher;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public int getC_time() {
        return c_time;
    }

    public void setC_time(int c_time) {
        this.c_time = c_time;
    }

    public int getC_duration() {
        return c_duration;
    }

    public void setC_duration(int c_duration) {
        this.c_duration = c_duration;
    }

    public int getC_startWeek() {
        return c_startWeek;
    }

    public void setC_startWeek(int c_startWeek) {
        this.c_startWeek = c_startWeek;
    }

    public int getC_endWeek() {
        return c_endWeek;
    }

    public void setC_endWeek(int c_endWeek) {
        this.c_endWeek = c_endWeek;
    }

    public int getC_day() {
        return c_day;
    }

    public void setC_day(int c_day) {
        this.c_day = c_day;
    }

    public String getC_room() {
        return c_room;
    }

    public void setC_room(String c_room) {
        this.c_room = c_room;
    }

    public String getC_teacher() {
        return c_teacher;
    }

    public void setC_teacher(String c_teacher) {
        this.c_teacher = c_teacher;
    }

    public int getColor(){
        return contentColor;
    }

    public void setColor(int color){
        this.contentColor = color;
    }

    public String getStartTime(){
        return start_times[c_time-1];
    }

    public String getEndTime(){
        return end_times[c_time+c_duration-2];
    }
}
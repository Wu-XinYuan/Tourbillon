package com.example.Tourbillon;


import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;


import java.util.Calendar;
import java.util.HashMap;

public class CourseDownloader {
    private String course_url = "https://i.sjtu.edu.cn/kbcx/xskbcx_cxXsKb.html";

    private static String convertParams(String term) {
        switch (term) {
            case "2":
                return "12";
            case "3":
                return "16";
            default:
                return "3";
        }
    }
    

    public FormBody getPostBody(String url) {
        String format_url = url.substring(53);

        String [] formats = format_url.split("&");

        HashMap<String,String> paramsMap=new HashMap<>();

        for(int i = 0; i < formats.length; ++i) {
            String [] key_value = formats[i].split("=");
            if(key_value[0] == "gnmkd") {
                paramsMap.put("gnmkd", key_value[1]);
            }
            if(key_value[0] == "su") {
                paramsMap.put("su", key_value[1]);
            }
        }

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            //追加表单信息
            builder.add(key, paramsMap.get(key));
        }
        //builder.add("Cookie", cookie);
        Calendar calendar = Calendar.getInstance();
		calendar.setTime(MainActivity.curDate);
		int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String term;

        if(month >= 8 || month < 2) {
            term = "1"; // 秋季学期
        }
        else if(month < 8) {
            term = "2"; // 春季学期
            year = year - 1;
        }
        else term = "3"; // 夏季学期
        
        builder.add("xnm", Integer.toString(year)); // 这里应该是主活动onCreate的时候读取系统时间，将系统时间存入主活动public static，然后再加一个转换函数，将时间转换为xnm，xqm
        builder.add("xqm", convertParams(term));

        FormBody body = builder.build();

        return body;
    }

    public void sendPostRequest(String cookie, String url, okhttp3.Callback callback) {
        FormBody body = this.getPostBody(url);
        Log.d("CourseDownloader", "start sending request!");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(course_url)
                .header("Cookie", cookie)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

}


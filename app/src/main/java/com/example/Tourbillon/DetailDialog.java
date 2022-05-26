package com.example.Tourbillon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class DetailDialog {
    private static AlertDialog dialog_detail;

    public interface DialogButtonOnClickListener {
        void onDelete(Class_t event);
        void onEdit(Class_t event);
    }

    @SuppressLint("SetTextI18n")
    public static void showEventDetailDialog(Activity activity, Class_t event,
                                             DialogButtonOnClickListener dialogButtonOnClickListener) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(activity).inflate(R.layout.event_detail,
                null);
        ImageButton button_delete = view.findViewById(R.id.button_detailDialog_delete);
        ImageButton button_edit = view.findViewById(R.id.button_detailDialog_edit);
        TextView textView_week = view.findViewById(R.id.TextView_detailDialog_Week);
        TextView textView_time = view.findViewById(R.id.TextView_detailDialog_Time);
        TextView textView_teacher = view.findViewById(R.id.TextView_detailDialog_Teacher);
        TextView textView_loacation = view.findViewById(R.id.TextView_detailDialog_location);

        textView_week.setText("第"+event.getC_startWeek()+'-'+event.getC_endWeek()+"周");
        textView_time.setText(event.getStartTime()+" - " +event.getEndTime());
        textView_teacher.setText(event.getC_teacher());
        textView_loacation.setText(event.getC_room());

        button_delete.setOnClickListener(view12 -> {
            dialogButtonOnClickListener.onDelete(event);
            dialog_detail.dismiss();
        });
        button_edit.setOnClickListener(view1 -> {
            dialogButtonOnClickListener.onEdit(event);
            dialog_detail.dismiss();
        });

        AlertDialog.Builder builder_editWeek = new AlertDialog.Builder(activity, R.style.dialog);
        builder_editWeek.setView(view);
        builder_editWeek.setCancelable(true);
        dialog_detail = builder_editWeek.create();
        dialog_detail.getWindow().setDimAmount(0.5f);
        dialog_detail.getWindow().setBackgroundDrawableResource(android.R.color.white);
        dialog_detail.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog_detail.setCanceledOnTouchOutside(true);
        dialog_detail.show();
    }
}

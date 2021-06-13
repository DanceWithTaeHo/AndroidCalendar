package com.example.androidcalendarproject2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TimePicker;

public class ScheduleActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener {
    private int month, year, hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        EditText title = (EditText)findViewById(R.id.schedule_title);
        TimePicker startTime = (TimePicker)findViewById(R.id.start_time);
        TimePicker finishTime = (TimePicker)findViewById(R.id.finish_time);

        startTime.setOnTimeChangedListener(this);
        finishTime.setOnTimeChangedListener(this);

        Intent intent = getIntent();

        month = intent.getIntExtra("month", 0);
        year = intent.getIntExtra("year", 0);

        title.setText(year+"년 " + (month+1) + "월 " + startTime.getCurrentHour() + "시");
    }


    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        hour = hourOfDay;
        min = minute;
    }
}
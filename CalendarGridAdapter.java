package com.example.androidcalendarproject2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.Serializable;
import java.util.ArrayList;

public class CalendarGridAdapter extends BaseAdapter { // 캘린더그리드 어뎁터 정의
    private View calendarView;
    private int resource;
    private ArrayList<DayItem> dayItems;
    private ArrayList<TextView> dayTextViews = new ArrayList<TextView>();
    private TextView dayTv;
    private int height, width;

    public CalendarGridAdapter(View calendarView, int resource, ArrayList<DayItem> dayItems, int width, int height){
        this.calendarView = calendarView;
        this.resource = resource;
        this.dayItems = dayItems;
        this.width = width;
        this.height = height;
    }

    @Override
    public int getCount() {
        return dayItems.size();
    }

    @Override
    public Object getItem(int position) {
        return dayItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setBackgroundColor(int position){
        for (int i=0; i<dayTextViews.size(); i++){
            dayTextViews.get(i).setBackgroundColor(Color.WHITE);
        }
        dayTextViews.get(position+1).setBackgroundColor(Color.CYAN);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) { // 해당 항목 뷰가 이전에 생성된 적이 없는 경우
            LayoutInflater inflater = (LayoutInflater) calendarView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 항목 뷰를 정의한 xml 리소스(여기서는 mResource 값)으로부터 항목 뷰 객체를 메모리로 로드
            convertView = inflater.inflate(resource, parent,false);
        }
        int textViewHeight = height/6;
        int textViewWidth = width/7;
        dayTv = convertView.findViewById(R.id.day);
        dayTv.setLayoutParams(new LinearLayout.LayoutParams(textViewWidth, textViewHeight));


        dayTv.setText(dayItems.get(position).getDay()); // 해당 요일을 TextView에 설정
        dayTextViews.add(dayTv);
        return convertView;
    }

}

package com.example.monthviewactivity;

import android.content.Context;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

public class CalendarGridAdapter extends BaseAdapter { // 캘 린더그리드 어뎁터 정의
    private Context context;
    private int resource;
    private ArrayList<DayItem> dayItems;

    public CalendarGridAdapter(Context context, int resource, ArrayList<DayItem> dayItems){
        this.context = context;
        this.resource = resource;
        this.dayItems = dayItems;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) { // 이전에 생성된 적이 없을 때
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // xml 리소스로부터 뷰 객체를 로드
            convertView = inflater.inflate(resource, parent, false);
        }
        TextView dayTv = convertView.findViewById(R.id.day);
        dayTv.setText(dayItems.get(position).getDay()); // 해당 요일을 TextView에 설정

        return convertView;
    }
}

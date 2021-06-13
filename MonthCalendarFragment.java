package com.example.androidcalendarproject2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MonthCalendarFragment extends Fragment {
    private int year, month, day, height, width, dayPosition; // 년, 월, 일, 뷰페이저 높이
    private Calendar calendar = Calendar.getInstance(); // 캘린더 객체

    private ArrayList<DayItem> dayItems; // 그리드 아이템 리스트
    private GridView calendarGrid; // 캘린더 그리드
    private CalendarGridAdapter calendarGridAdapter; // 캘린더 그리드 객체 어뎁터
    private FloatingActionButton addScheduleBtn;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    public MonthCalendarFragment() {
        dayItems = new ArrayList<DayItem>(); // 그리드 아이템
    }

    public static MonthCalendarFragment newInstance(int year, int month, int day, int width, int height) {
        MonthCalendarFragment fragment = new MonthCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        args.putInt(ARG_PARAM3, day);
        args.putInt(ARG_PARAM4, width);
        args.putInt(ARG_PARAM5, height);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = getArguments().getInt(ARG_PARAM1);
            month = getArguments().getInt(ARG_PARAM2);
            day = getArguments().getInt(ARG_PARAM3);
            width = getArguments().getInt(ARG_PARAM4);
            height = getArguments().getInt(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View calendarView = inflater.inflate(R.layout.fragment_month_calendar, container, false);
        setCalendar(); // 캘린더 설정, 날짜에 맞게 dayItem 삽입
        calendarGrid = (GridView) calendarView.findViewById(R.id.calendar_grid); // 캘린더 그리드 뷰
        addScheduleBtn = (FloatingActionButton) this.getActivity().findViewById(R.id.add_schedule_btn);
        calendarGridAdapter = new CalendarGridAdapter(calendarView, R.layout.day, dayItems, width, height); // 그리드 어뎁터
        calendarGrid.setAdapter(calendarGridAdapter); // 어뎁터 설정

        // 이벤트 리스너 설정, 아래에 내부클래스로 정의
        calendarGrid.setOnItemClickListener(new GridClickListener());
        addScheduleBtn.setOnClickListener(new addScheduleListener());
        return calendarView;
    }
    private void setCalendar(){ // 캘린더 객체를 통해 날짜를 셋팅
        // 날짜 세팅, 년/월/일
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1); // 먼저 요일을 알기 위해 해당 달의 시작요일로 설정
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 셋팅된 날짜로 요일을 구함
        int boxs = 42;
        for(int i=1; i<dayOfWeek; i++){ // 시작요일을 맞추기 위해 앞에 요일들을 공백으로 채움
            if(dayOfWeek>7) break; // 시작일이 일요일이라면 공백을 채우지 않음
            boxs--;
            dayItems.add(new DayItem(Integer.toString(year), Integer.toString(month+1), ""));
        }
        calendar.set(Calendar.DATE, day); // 현재 요일로 변경

        // 설정된 달의 일 수 만큼 루프를 돌면서 아이템 삽입
        for(int i=0; i<calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
            int day = i+1;
            boxs--;
            dayItems.add(new DayItem(Integer.toString(year), Integer.toString(month+1), Integer.toString(day))); // 문자형으로 변환하여 날짜 삽입
        }
        for(int i=0; i<boxs; i++)
            dayItems.add(new DayItem(Integer.toString(year), Integer.toString(month+1), ""));
    }
    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    class addScheduleListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity().getApplicationContext(), ScheduleActivity.class);
            intent.putExtra("month", month);
            intent.putExtra("year", year);
            startActivity(intent);
        }
    }




    /* 아이템 클릭 리스너를 구현해 Toast 메시지를 생성하는 이벤트 리스너*/
    class GridClickListener implements AdapterView.OnItemClickListener { // 토스트 메시지 이벤트 리스너
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            dayPosition = position;
            // calendarGridAdapter로부터 선택된 Item을 가져와 현재 context에 dayItem 날짜 정보를 출력
            calendarGridAdapter.setBackgroundColor(position);
        }
    }
}

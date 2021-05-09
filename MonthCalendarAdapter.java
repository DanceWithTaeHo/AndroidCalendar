package com.example.androidcalendarproject2;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MonthCalendarAdapter extends FragmentStateAdapter {
    HashMap<Integer, MonthCalendarFragment> calendarFragmentHashMap = new HashMap<Integer, MonthCalendarFragment>();
    MonthCalendarFragment calendarFragment;
    private static int NUM_ITEMS=101; // 페이지 개수
    public int STARTPOSITION = (NUM_ITEMS-1)/2; // 시작 페이지
    private int year, month, day, thisYear, thisMonth, today; // 년, 월, 일
    private Calendar calendar = Calendar.getInstance(); // 캘린더 객체
    private ViewPager2 viewPager2;


    public MonthCalendarAdapter(@NonNull Fragment fragment, ViewPager2 viewPager2) {
        super(fragment);
        this.viewPager2 = viewPager2;

        // 현재 날짜를 가져옴
        thisYear = calendar.get(Calendar.YEAR);
        thisMonth = calendar.get(Calendar.MONTH);
        today = calendar.get(Calendar.DATE);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        setDate(position);
        calendarFragment = MonthCalendarFragment.newInstance(year, month, day);
        calendarFragmentHashMap.put(position, calendarFragment);
        return calendarFragment;
    }

    private void setDate(int position){ // 새로운 intent 시작시 날짜 설정해줌
        int index = position-STARTPOSITION;

        calendar.set(Calendar.YEAR, thisYear);
        calendar.set(Calendar.MONTH, thisMonth);
        calendar.set(Calendar.DATE, today);

        if(index!=0) { // 페이지의 중간, 첫 시작점
            month = thisMonth+index;
            calendar.set(Calendar.MONTH, month);
        }
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);
    }
    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
    public int getYear(int position) {
        return calendarFragmentHashMap.get(position).getYear();
    }
    public int getMonth(int position) {
        return calendarFragmentHashMap.get(position).getMonth();
    }
}

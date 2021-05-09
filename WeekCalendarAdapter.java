package com.example.androidcalendarproject2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Calendar;
import java.util.HashMap;

public class WeekCalendarAdapter extends FragmentStateAdapter {
    HashMap<Integer, WeekCalendarFragment> calendarFragmentHashMap = new HashMap<Integer, WeekCalendarFragment>();
    WeekCalendarFragment calendarFragment;
    private static int NUM_ITEMS=5; // 페이지 개수
    private int year, month, day, thisYear, thisMonth, today; // 년, 월, 일
    private Calendar calendar = Calendar.getInstance(); // 캘린더 객체
    private ViewPager2 viewPager2;


    public WeekCalendarAdapter(@NonNull Fragment fragment, ViewPager2 viewPager2) {
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
        calendarFragment = WeekCalendarFragment.newInstance(year, month, day, position);
        calendarFragmentHashMap.put(position, calendarFragment);
        return calendarFragment;
    }

    private void setDate(int position){ // 새로운 intent 시작시 날짜 설정해줌

        calendar.set(Calendar.YEAR, thisYear);
        calendar.set(Calendar.MONTH, thisMonth);
        calendar.set(Calendar.DATE, 1);

        if(position!=0) { // 페이지의 중간, 첫 시작점
            day = 1+((position+1)*7);
            calendar.set(Calendar.DATE, day);
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

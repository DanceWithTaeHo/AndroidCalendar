package com.example.androidcalendarproject2;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Calendar;

public class MonthViewFragment extends Fragment {
    Calendar calendar = Calendar.getInstance();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private  AppCompatActivity mainActivity;

    public MonthViewFragment() {}
    public MonthViewFragment(AppCompatActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public static MonthViewFragment newInstance(String param1, String param2) {
        MonthViewFragment fragment = new MonthViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_month_view, container, false);

        ViewPager2 vpPager = rootView.findViewById(R.id.vpPager);
        MonthCalendarAdapter adapter = new MonthCalendarAdapter(this, vpPager);
        vpPager.setAdapter(adapter);

        vpPager.setCurrentItem(adapter.STARTPOSITION, false); // 시작 페이지 설정
        mainActivity.getSupportActionBar().setTitle(calendar.get(Calendar.YEAR) + "년 " + (calendar.get(Calendar.MONTH)+1)+ "월");

        // 페이지 넘길때마다 호출
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mainActivity.getSupportActionBar().setTitle(adapter.getYear(position)+ "년 " + (adapter.getMonth(position)+1)+ "월");
            }
        });

        return rootView;
    }
}
package com.example.monthviewactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthViewActivity extends AppCompatActivity {
    private int year, month, day; // 년, 월, 일
    private Calendar calendar = Calendar.getInstance(); // 캘린더 객체

    private TextView yearMonthTextView; // 날짜 텍스트 뷰
    private ArrayList<DayItem> dayItems; // 그리드 아이템 리스트
    private GridView calendarGrid; // 캘린더 그리드
    private CalendarGridAdapter calendarGridAdapter; // 캘린더 그리드 객체 어뎁터

    private Button nextButton, beforeButton; // 버튼 객체

    private Intent intent; // 인텐트 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // 선언한 객체들 생성
        yearMonthTextView = (TextView)findViewById(R.id.yearMonthTextView); // 날짜를 표시하는 텍스트 뷰
        calendarGrid = (GridView)findViewById(R.id.calendar_grid); // 캘린더 그리드 뷰
        dayItems = new ArrayList<DayItem>(); // 그리드 아이템
        calendarGridAdapter = new CalendarGridAdapter(this, R.layout.day, dayItems); // 그리드 어뎁터
        beforeButton = (Button)findViewById(R.id.beforeButton); // 이전 버튼
        nextButton = (Button)findViewById(R.id.nextButton); // 다음 버튼
        intent = this.getIntent(); // 인텐트 생성
        
        initDate(); // 날짜 초기화
        setCalendar(); // 캘린더 설정, 날짜에 맞게 dayItem 삽입
        
        yearMonthTextView.setText(year+"년 " + (month+1) + "월");

        calendarGrid.setAdapter(calendarGridAdapter); // 어뎁터 설정

        // 이벤트 리스너 설정, 아래에 내부클래스로 정의
        calendarGrid.setOnItemClickListener(new ToastDateListener());
        beforeButton.setOnClickListener(new IntentListener(intent, false));
        nextButton.setOnClickListener(new IntentListener(intent, true));


    }
    private void initDate(){ // 새로운 intent 시작시 날짜 설정해줌
        year = intent.getIntExtra("year", -1); // 시작시 인텐트에 저장된 값이 있으면 키값으로 가져옴
        month = intent.getIntExtra("month", -1);
        day = intent.getIntExtra("day", 0);
        if(year == -1 || month == -1){ // 첫 시작시
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH); // 월에 경우 0~11의 값을 가짐
            day = calendar.get(Calendar.DATE);
        }
    }
    private void setCalendar(){ // 캘린더 객체를 통해 날짜를 셋팅
        // 날짜 세팅, 년/월/일
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1); // 먼저 요일을 알기 위해 해당 달의 시작요일로 설정
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 셋팅된 날짜로 요일을 구함

        for(int i=1; i<dayOfWeek; i++){ // 시작요일을 맞추기 위해 앞에 요일들을 공백으로 채움
            if(dayOfWeek>7) break; // 시작일이 일요일이라면 공백을 채우지 않음
            dayItems.add(new DayItem(Integer.toString(year), Integer.toString(month+1), ""));
        }
        calendar.set(Calendar.DATE, day); // 현재 요일로 변경

        // 설정된 달의 일 수를 만큼 루프를 돌면서 아이템 삽입
        for(int i=0; i<calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
            int day = i+1;
            dayItems.add(new DayItem(Integer.toString(year), Integer.toString(month+1), Integer.toString(day))); // 문자형으로 변환하여 날짜 삽입
        }
    }

    /* 새로운 인텐트를 생성하는 이벤트 리스너 */
    class IntentListener implements OnClickListener{
        Intent intent;
        boolean next; // 버튼이 다음인지 이전인지 판단

        public IntentListener(Intent intent, boolean next){
            this.intent = intent;
            this.next = next;
        }

        @Override
        public void onClick(View v) { // 날짜 정보와 함께 새로운 인텐트를 생성함
            Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
            chkDate(); // 날짜 확인 함수
            intent.putExtra("month", month);
            intent.putExtra("year", year);
            intent.putExtra("day", day);
            startActivity(intent); // 인텐트 시작
            finish(); // 피니쉬가 없으면 계속 누를떄마다 스택처럼 쌓임
        }
        private void chkDate(){ // 이전인지 다음버튼인지 확인하여 month를 조정함
            month = (next == true) ? month + 1 : month - 1;
            if(month<0){ // 만약 month가 1월보다 작다면 
                year--; // year를 1년 감소
                month = 11; // month 값 0~11, month를 12월로
            } else if(month>11){ // 만약 month가 12월을 넘어간다면
                year++; // year를 1년 증가
                month = 0; // month를 1월로
            }
        }
    }
    /* 아이템 클릭 리스너를 구현해 Toast 메시지를 생성하는 이벤트 리스너*/
    class ToastDateListener implements AdapterView.OnItemClickListener { // 토스트 메시지 이벤트 리스너
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // String date = ((DayItem)calendarGridAdapter.getItem(position)).getDate();
            //Toast.makeText(MonthViewActivity.this, date, Toast.LENGTH_SHORT).show();
            
            // calendarGridAdapter로부터 선택된 Item을 가져와 현재 context에 dayItem 날짜 정보를 출력
            DayItem dayItem = (DayItem)calendarGridAdapter.getItem(position);
            Toast.makeText(MonthViewActivity.this, dayItem.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
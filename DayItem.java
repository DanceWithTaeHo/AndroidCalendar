package com.example.monthviewactivity;

public class DayItem { // 캘린더 그리드의 아이템
    private String year, month, day; // 년, 월, 일

    public DayItem(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public String getDate() {
        return year + "." + month + "." + day;
    }
    public String getDay(){
        return day;
    }
    @Override
    public String toString(){
        return getDate();
    }
}

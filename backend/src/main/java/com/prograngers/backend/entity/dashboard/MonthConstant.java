package com.prograngers.backend.entity.dashboard;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Arrays;

public enum MonthConstant {
    JAN(1),
    FEB(2),
    MAR(3),
    APR(4),
    MAY(5),
    JUN(6),
    JUL(7),
    AUG(8),
    SEP(9),
    OCT(10),
    NOV(11),
    DEC(12);

    private final int month;

    public static MonthConstant getMonthConstant(int month){
        return Arrays.stream(MonthConstant.values()).filter(monthConstant -> monthConstant.getMonth() == month).findAny().get();
    }

    public int getMonth(){
        return month;
    }
    MonthConstant(int month) {
        this.month = month;
    }

    public LocalDateTime getStartDate(int year){
        return LocalDateTime.of(year, this.month, 1, 0, 0);
    }

    public LocalDateTime getEndDate(int year){
        return LocalDateTime.of(year, this.month, getLastDayOfMonth(year), 23, 59);
    }

    public int getLastDayOfMonth(int year) {
        return YearMonth.of(year, month).lengthOfMonth();
    }
}

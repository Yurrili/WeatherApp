package com.klaole.weatherapp.main_activity;

import android.content.Context;

import com.klaole.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateProviderImpl implements DateProvider {

    private static final int DAYS_IN_WEEK = 7;

    private SimpleDateFormat format;

    public DateProviderImpl(Context ctx) {
        String format = ctx.getString(R.string.date_formater);
        this.format = new SimpleDateFormat(format, Locale.getDefault());
    }


    @Override
    public String getFormattedTodayDate() {
        Date date = new Date();
        return format.format(date);
    }

    @Override
    public List<String> getListOfDates() {
        Date date = new Date();
        List<String> list = new ArrayList<>();
        list.add(getFormattedTodayDate());
        for (int i = 0; i < DAYS_IN_WEEK - 1; i++) {
            date = addDays(date, 1);
            list.add(format.format(date));
        }
        return list;
    }

    private static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}

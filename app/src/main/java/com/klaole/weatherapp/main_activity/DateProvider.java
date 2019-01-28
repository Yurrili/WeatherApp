package com.klaole.weatherapp.main_activity;

import java.util.List;

public interface DateProvider {

    String getFormatedTodayDate();

    List<String> getListOfDates();

}

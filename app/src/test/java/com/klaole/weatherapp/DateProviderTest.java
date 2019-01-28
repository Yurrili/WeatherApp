package com.klaole.weatherapp;

import android.content.Context;

import com.klaole.weatherapp.main_activity.DateProviderImpl;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DateProviderTest {

    private DateProviderImpl dateProvider;

    @Before
    public void setUp() {
        Context ctx = mock(Context.class);
        when(ctx.getString(anyInt())).thenReturn("yyyy/MM/dd");
        dateProvider = new DateProviderImpl(ctx);
    }

    @Test
    public void checkTodaysDateProcessingTest() {
        String today = dateProvider.getFormatedTodayDate();

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String formated = format.format(date);

        assertEquals(formated, today);
    }

}
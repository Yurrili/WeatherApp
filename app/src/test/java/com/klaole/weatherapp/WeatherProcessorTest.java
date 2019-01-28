package com.klaole.weatherapp;

import com.klaole.weatherapp.main_activity.WeatherProcessor;
import com.klaole.weatherapp.models.ConsolidatedWeather;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WeatherProcessorTest {

    private List<ConsolidatedWeather> args;

    @Before
    public void setUp() {

        ConsolidatedWeather consolidatedWeather1 = new ConsolidatedWeather();
        consolidatedWeather1.setApplicableDate("2013-04-27");
        consolidatedWeather1.setWeatherStateAbbr("cloudy");
        consolidatedWeather1.setMinTemp(2.0);
        consolidatedWeather1.setMaxTemp(0.0);

        ConsolidatedWeather consolidatedWeather2 = new ConsolidatedWeather();
        consolidatedWeather2.setApplicableDate("2013-04-27");
        consolidatedWeather2.setWeatherStateAbbr("cloudy");
        consolidatedWeather2.setMinTemp(4.0);
        consolidatedWeather2.setMaxTemp(2.0);

        args = Arrays.asList(consolidatedWeather1, consolidatedWeather2);
    }

    @Test
    public void checkProcessing() {
        ConsolidatedWeather result = WeatherProcessor.getOneDayForecast(args);

        assertEquals(new Double(2.0), result.getTheTemp());
        assertEquals("cloudy", result.getWeatherStateAbbr());
    }
}
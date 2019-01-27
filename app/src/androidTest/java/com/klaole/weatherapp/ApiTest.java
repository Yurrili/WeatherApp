package com.klaole.weatherapp;

import android.util.Log;

import com.klaole.weatherapp.models.Forecast;
import com.klaole.weatherapp.models.LocationSearch;
import com.klaole.weatherapp.network.GetForecastDataService;
import com.klaole.weatherapp.network.RetrofitInstance;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.TestCase.assertTrue;

public class ApiTest {

    private static final int testId = 523920;
    private static final String testCity = "Warsaw";

    @Test
    public void locationSearchTest() {

        GetForecastDataService apiEndpoints = RetrofitInstance.getRetrofitInstance().create(GetForecastDataService.class);

        Call<List<LocationSearch>> call = apiEndpoints.getLocationSearch(testCity);

        try {
            Response<List<LocationSearch>> response = call.execute();
            LocationSearch locationSearchResponse = Objects.requireNonNull(response.body()).get(0);
            Log.d("LocationSearchTest", locationSearchResponse.getTitle());
            assertTrue(response.isSuccessful() && locationSearchResponse.getWoeid() == testId);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getForecastTest() {

        GetForecastDataService apiEndpoints = RetrofitInstance.getRetrofitInstance().create(GetForecastDataService.class);

        Call<Forecast> call = apiEndpoints.getForecastData(testId);

        try {
            Response<Forecast> response = call.execute();
            Forecast forecastResponse = Objects.requireNonNull(response.body());
            Log.d("LocationSearchTest", forecastResponse.getTitle());
            assertTrue(response.isSuccessful() && forecastResponse.getTitle().equals(testCity));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

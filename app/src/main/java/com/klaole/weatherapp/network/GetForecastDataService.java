package com.klaole.weatherapp.network;

import com.klaole.weatherapp.models.Forecast;
import com.klaole.weatherapp.models.LocationSearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetForecastDataService {


    @GET("location/search/")
    Call<List<LocationSearch>> getLocationSearch(@Query("query") String name);

    @GET("/api/location/{id}")
    Call<Forecast> getForecastData(@Path("id") int id);


}
package com.klaole.weatherapp.network;

import com.klaole.weatherapp.models.ConsolidatedWeather;
import com.klaole.weatherapp.models.Forecast;
import com.klaole.weatherapp.models.LocationSearch;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetForecastDataService {


    @GET("location/search/")
    Observable<List<LocationSearch>> getLocationSearch(@Query("query") String name);

    @GET("/api/location/{id}")
    Observable<Forecast> getForecastData(@Path("id") int id);

    @GET("/api/location/{id}/{date}")
    Observable<List<ConsolidatedWeather>> getLocationForDay(@Path("id") int id, @Path("date") String date);


}
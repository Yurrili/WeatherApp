package com.klaole.weatherapp.main_activity;

import android.util.Log;

import com.klaole.weatherapp.models.Forecast;
import com.klaole.weatherapp.models.LocationSearch;
import com.klaole.weatherapp.network.GetForecastDataService;
import com.klaole.weatherapp.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetForecastInteractorImpl implements MainContract.GetForecastInteractor {

    private LocationSearch location;

    @Override
    public void getForecastArrayList(final OnFinishedListener onFinishedListener) {


        /** Create handle for the RetrofitInstance interface*/
        GetForecastDataService service = RetrofitInstance.getRetrofitInstance().create(GetForecastDataService.class);

        Call<Forecast> call = service.getForecastData(location.getWoeid());

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                onFinishedListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getLocationSearch(String name, final OnFinishedSearchListener onFinishedListener) {

        /** Create handle for the RetrofitInstance interface*/
        GetForecastDataService service = RetrofitInstance.getRetrofitInstance().create(GetForecastDataService.class);

        Call<List<LocationSearch>> call = service.getLocationSearch(name);

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<List<LocationSearch>>() {
            @Override
            public void onResponse(Call<List<LocationSearch>> call, Response<List<LocationSearch>> response) {
                onFinishedListener.onFinishedSearch(response.body());
            }

            @Override
            public void onFailure(Call<List<LocationSearch>> call, Throwable t) {
                onFinishedListener.onFailedSearch(t);
            }
        });
    }


}
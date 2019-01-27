package com.klaole.weatherapp.main_activity;

import android.util.Log;

import com.klaole.weatherapp.models.ConsolidatedWeather;
import com.klaole.weatherapp.models.Forecast;
import com.klaole.weatherapp.models.LocationSearch;
import com.klaole.weatherapp.network.GetForecastDataService;
import com.klaole.weatherapp.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetForecastInteractorImpl implements MainContract.GetForecastInteractor {

    private LocationSearch location;

    @Override
    public void getForecastArrayList(final OnFinishedListener onFinishedListener) {


        /** Create handle for the RetrofitInstance interface*/
        GetForecastDataService service = RetrofitInstance.getRetrofitInstance().create(GetForecastDataService.class);

        Observable<Forecast> call = service.getForecastData(location.getWoeid());

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Forecast>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Forecast value) {
                        onFinishedListener.onFinished(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFinishedListener.onFailure(e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("API Call", "onComplete");
                    }
                });

    }

    @Override
    public void getLocationSearch(String name, final OnFinishedSearchListener onFinishedListener) {

        /** Create handle for the RetrofitInstance interface*/
        GetForecastDataService service = RetrofitInstance.getRetrofitInstance().create(GetForecastDataService.class);

        Observable<List<LocationSearch>> call = service.getLocationSearch(name);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<LocationSearch>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<LocationSearch> value) {
                        onFinishedListener.onFinishedSearch(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFinishedListener.onFailedSearch(e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("API Call", "onComplete");
                    }
                });


    }

    @Override
    public void get7daysForecast(int id, List<String> dates, OnFinishedForecastListener onFinishedListener) {
        /** Create handle for the RetrofitInstance interface*/
        GetForecastDataService service = RetrofitInstance.getRetrofitInstance().create(GetForecastDataService.class);

        List<Observable<List<ConsolidatedWeather>>> list = new ArrayList<>();

        for (String date : dates) {

            Observable<List<ConsolidatedWeather>> job = service.getLocationForDay(id, date);

            list.add(job.subscribeOn(Schedulers.newThread()));

        }


//        Observable.zip(list, (d1, d2, d3, d4, d5, d6, d7) -> {
//            System.out.println(d1);
//        }).subscribe(result -> System.out);

    }


//        Call<List<ConsolidatedWeather>> call = service.getLocationForDay(id, date);
//
//        Log.wtf("URL Called", call.request().url() + "");
//
//        call.enqueue(new Callback<List<ConsolidatedWeather>>() {
//            @Override
//            public void onResponse(Call<List<ConsolidatedWeather>> call, Response<List<ConsolidatedWeather>> response) {
//                onFinishedListener.onFinishedForecast(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<ConsolidatedWeather>> call, Throwable t) {
//                onFinishedListener.onFailedForecast(t);
//            }
//        });
//}


}
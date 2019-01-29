package com.klaole.weatherapp.main_activity;

import android.util.Log;

import com.klaole.weatherapp.models.ConsolidatedWeather;
import com.klaole.weatherapp.models.Forecast;
import com.klaole.weatherapp.models.LocationSearch;
import com.klaole.weatherapp.network.GetForecastDataService;
import com.klaole.weatherapp.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetForecastInteractorImpl implements MainContract.GetForecastInteractor {

    @Inject
    public GetForecastInteractorImpl() {
    }


    @Override
    public void getForecastArrayList(int id, final OnFinishedListener onFinishedListener) {


        /** Create handle for the RetrofitInstance interface*/
        GetForecastDataService service = RetrofitInstance.getRetrofitInstance().create(GetForecastDataService.class);

        Observable<Forecast> call = service.getForecastData(id);

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

        Observable.zip(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6),
                (list1, list2, list3, list4, list5, list6, list7) -> {
                    TreeMap<String, ConsolidatedWeather> weatherForecastFor7days = new TreeMap<>();

                    ConsolidatedWeather weather1 = WeatherProcessor.getOneDayForecast(list1);
                    weatherForecastFor7days.put(weather1.getApplicableDate(), weather1);
                    ConsolidatedWeather weather2 = WeatherProcessor.getOneDayForecast(list2);
                    weatherForecastFor7days.put(weather2.getApplicableDate(), weather2);
                    ConsolidatedWeather weather3 = WeatherProcessor.getOneDayForecast(list3);
                    weatherForecastFor7days.put(weather3.getApplicableDate(), weather3);
                    ConsolidatedWeather weather4 = WeatherProcessor.getOneDayForecast(list4);
                    weatherForecastFor7days.put(weather4.getApplicableDate(), weather4);
                    ConsolidatedWeather weather5 = WeatherProcessor.getOneDayForecast(list5);
                    weatherForecastFor7days.put(weather5.getApplicableDate(), weather5);
                    ConsolidatedWeather weather6 = WeatherProcessor.getOneDayForecast(list6);
                    weatherForecastFor7days.put(weather6.getApplicableDate(), weather6);
                    ConsolidatedWeather weather7 = WeatherProcessor.getOneDayForecast(list7);
                    weatherForecastFor7days.put(weather7.getApplicableDate(), weather7);

                    return weatherForecastFor7days;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TreeMap<String, ConsolidatedWeather>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TreeMap<String, ConsolidatedWeather> response) {
                        onFinishedListener.onFinishedForecast(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFinishedListener.onFailedForecast(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
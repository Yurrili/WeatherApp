package com.klaole.weatherapp;

import com.klaole.weatherapp.main_activity.WeatherProcessor;
import com.klaole.weatherapp.models.ConsolidatedWeather;
import com.klaole.weatherapp.models.Forecast;
import com.klaole.weatherapp.models.LocationSearch;
import com.klaole.weatherapp.network.GetForecastDataService;
import com.klaole.weatherapp.network.RetrofitInstance;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static junit.framework.TestCase.assertEquals;

public class ApiTest {

    private static final int testId = 523920;
    private static final String testCity = "Warsaw";
    private static final String longlatCity = "19.9143558,50.0282059";
    private static final List<String> testDates = Arrays.asList("2019/1/27", "2019/1/26", "2019/1/25", "2019/1/24", "2019/1/23", "2019/1/22", "2019/1/21");

    @BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Test
    public void locationSearchTest() {

        GetForecastDataService apiEndpoints = RetrofitInstance.getRetrofitInstance().create(GetForecastDataService.class);

        Observable<List<LocationSearch>> call = apiEndpoints.getLocationSearch(longlatCity);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<LocationSearch>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<LocationSearch> value) {
                        assertEquals((int) value.get(0).getWoeid(), testId);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });


    }

    @Test
    public void getForecastTest() {

        GetForecastDataService apiEndpoints = RetrofitInstance.getRetrofitInstance().create(GetForecastDataService.class);

        Observable<Forecast> call = apiEndpoints.getForecastData(testId);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Forecast>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Forecast value) {
                        assertEquals(value.getTitle(), testCity);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }


    @Test
    public void getLocationForDayTest() {

        GetForecastDataService apiEndpoints = RetrofitInstance.getRetrofitInstance().create(GetForecastDataService.class);

        List<Observable<List<ConsolidatedWeather>>> list = new ArrayList<>();

        for (String date : testDates) {
            Observable<List<ConsolidatedWeather>> job = apiEndpoints.getLocationForDay(testId, date);
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
                        for (Map.Entry<String, ConsolidatedWeather> ele : response.entrySet()) {
                            System.out.println("Key: " + ele.getKey());
                            System.out.println("Test temp: " + ele.getValue().getTheTemp());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Error");
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

}

package com.klaole.weatherapp;

import com.klaole.weatherapp.models.Forecast;
import com.klaole.weatherapp.models.LocationSearch;
import com.klaole.weatherapp.network.GetForecastDataService;
import com.klaole.weatherapp.network.RetrofitInstance;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
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
    private static final String testDate = "2019/1/27";
    private static final String testWeatherCondition = "Hail";

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

        Observable<List<LocationSearch>> call = apiEndpoints.getLocationSearch(testCity);

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

//        Call<List<ConsolidatedWeather>> call = apiEndpoints.getLocationForDay(testId, testDate);
//
//        try {
//            Response<List<ConsolidatedWeather>> response = call.execute();
//            List<ConsolidatedWeather> forecastResponse = Objects.requireNonNull(response.body());
//            assertTrue(response.isSuccessful() && forecastResponse.get(0).getWeatherStateName().equals(testWeatherCondition));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

}

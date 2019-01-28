package com.klaole.weatherapp.main_activity;

import android.util.Log;

import com.klaole.weatherapp.models.ConsolidatedWeather;
import com.klaole.weatherapp.models.Forecast;
import com.klaole.weatherapp.models.LocationSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MainPresenterImpl implements MainContract.Presenter,
        MainContract.GetForecastInteractor.OnFinishedListener, MainContract.GetForecastInteractor.OnFinishedSearchListener, MainContract.GetForecastInteractor.OnFinishedForecastListener {

    private MainContract.MainView mainView;
    private MainContract.GetForecastInteractor getForecastInteractor;
    private DateProvider dateProvider;

    private int locationId = 523920;


    public MainPresenterImpl(DateProvider dateProvider, MainContract.MainView mainView, MainContract.GetForecastInteractor getForecastInteractor) {
        this.mainView = mainView;
        this.getForecastInteractor = getForecastInteractor;
        this.dateProvider = dateProvider;
    }

    @Override
    public void onDestroy() {

        mainView = null;

    }

    @Override
    public void onRefreshButtonClick() {

        if (mainView != null) {
            mainView.showProgress();
        }

        requestDataFromServer();

    }

    @Override
    public void requestDataFromServer() {
        Log.i("Presenter", String.format("Call requestDataFromServer - location %1d", locationId));
        getForecastInteractor.get7daysForecast(locationId, dateProvider.getListOfDates(), this);
    }

    @Override
    public void searchLocation(String name) {

        getForecastInteractor.getLocationSearch(name, this);
    }


    @Override
    public void onFinished(Forecast weatherArrayList) {
        if (mainView != null) {
            Log.i("Presenter", String.format("Set data for recycler, size() = %1d", weatherArrayList.getConsolidatedWeather().size()));
            mainView.setDataToRecyclerView(weatherArrayList.getConsolidatedWeather());
            mainView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (mainView != null) {
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }

    @Override
    public void onFinishedSearch(List<LocationSearch> locationSearch) {
        if (mainView != null) {
            requestDataFromServer();
            mainView.hideProgress();
        }
    }

    @Override
    public void onFailedSearch(Throwable t) {
        if (mainView != null) {
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }

    @Override
    public void onFinishedForecast(TreeMap<String, ConsolidatedWeather> forecast) {
        if (mainView != null) {
            Log.i("Presenter", String.format("Set data for recycler, size() = %1d", forecast.size()));
            mainView.setDataToRecyclerView(new ArrayList<>(forecast.values()));
            mainView.hideProgress();
        }
    }

    @Override
    public void onFailedForecast(Throwable t) {
        if (mainView != null) {
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }
}

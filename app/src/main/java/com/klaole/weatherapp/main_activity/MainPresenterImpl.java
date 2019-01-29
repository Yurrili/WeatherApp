package com.klaole.weatherapp.main_activity;

import android.util.Log;

import com.klaole.weatherapp.models.ConsolidatedWeather;
import com.klaole.weatherapp.models.Forecast;
import com.klaole.weatherapp.models.LocationSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.inject.Inject;

public class MainPresenterImpl implements MainContract.Presenter,
        MainContract.GetForecastInteractor.OnFinishedListener, MainContract.GetForecastInteractor.OnFinishedSearchListener, MainContract.GetForecastInteractor.OnFinishedForecastListener {

    private MainContract.MainView mainView;
    private MainContract.Fragment mainFragment;
    private MainContract.GetForecastInteractor getForecastInteractor;
    private DateProvider dateProvider;


    private int locationId;

    @Inject
    public MainPresenterImpl(DateProvider dateProvider, MainContract.MainView mainView, MainContract.Fragment fragment, MainContract.GetForecastInteractor getForecastInteractor) {
        this.mainView = mainView;
        this.mainFragment = fragment;
        this.getForecastInteractor = getForecastInteractor;
        this.dateProvider = dateProvider;
    }

    @Override
    public void onDestroy() {

        mainView = null;
    }


    @Override
    public void requestDataFromServer() {

        if (mainView != null) {
            mainView.showProgress();
        }


        Log.i("Presenter", String.format("Call requestDataFromServer - location %1d", locationId));
        getForecastInteractor.get7daysForecast(locationId, dateProvider.getListOfDates(), this);
    }

    @Override
    public void searchLocation(double latitude, double longitude) {

        if (mainView != null) {
            mainView.showProgress();
        }

        getForecastInteractor.getLocationSearch(latitude, longitude, this);
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
        locationId = locationSearch.get(0).getWoeid();
        Log.i("LocationSearch", "Name " + locationSearch.get(0).getTitle());
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
            List<ConsolidatedWeather> list = new ArrayList<>(forecast.values());
            mainFragment.setData(list.get(0));
            mainView.setDataToRecyclerView(list.subList(1, list.size()));
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

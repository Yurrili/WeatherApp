package com.klaole.weatherapp.main_activity;

import com.klaole.weatherapp.models.Forecast;
import com.klaole.weatherapp.models.LocationSearch;

import java.util.List;

public class MainPresenterImpl implements MainContract.Presenter, MainContract.GetForecastInteractor.OnFinishedListener, MainContract.GetForecastInteractor.OnFinishedSearchListener {

    private MainContract.MainView mainView;
    private MainContract.GetForecastInteractor getForecastInteractor;

    public MainPresenterImpl(MainContract.MainView mainView, MainContract.GetForecastInteractor getForecastInteractor) {
        this.mainView = mainView;
        this.getForecastInteractor = getForecastInteractor;
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

        getForecastInteractor.getForecastArrayList(this);
    }

    @Override
    public void searchLocation(String name) {

        getForecastInteractor.getLocationSearch(name, this);
    }


    @Override
    public void onFinished(Forecast weatherArrayList) {
        if (mainView != null) {
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
}

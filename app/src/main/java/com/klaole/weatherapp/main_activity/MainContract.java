package com.klaole.weatherapp.main_activity;

import com.klaole.weatherapp.models.ConsolidatedWeather;
import com.klaole.weatherapp.models.Forecast;
import com.klaole.weatherapp.models.LocationSearch;

import java.util.List;

public interface MainContract {

    /**
     * Call when user interact with the view and other when view OnDestroy()
     */
    interface Presenter {

        void onDestroy();

        void onRefreshButtonClick();

        void requestDataFromServer();

        void searchLocation(String name);
    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetForecastInteractorImpl class
     **/
    interface MainView {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<ConsolidatedWeather> forecasts);

        void setDataToFragment(Forecast forecast);

        void onResponseFailure(Throwable throwable);

    }

    /**
     * Fetch data from your web service.
     **/
    interface GetForecastInteractor {

        interface OnFinishedListener {
            void onFinished(Forecast weatherArrayList);

            void onFailure(Throwable t);
        }

        interface OnFinishedSearchListener {
            void onFinishedSearch(List<LocationSearch> locationSearch);

            void onFailedSearch(Throwable t);
        }

        void getForecastArrayList(OnFinishedListener onFinishedListener);

        void getLocationSearch(String name, OnFinishedSearchListener onFinishedListener);

    }

}
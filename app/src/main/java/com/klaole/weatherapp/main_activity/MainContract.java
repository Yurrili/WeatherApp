package com.klaole.weatherapp.main_activity;

import com.klaole.weatherapp.models.ConsolidatedWeather;
import com.klaole.weatherapp.models.Forecast;
import com.klaole.weatherapp.models.LocationSearch;

import java.util.List;
import java.util.TreeMap;

public interface MainContract {

    /**
     * Call when user interact with the view and other when view OnDestroy()
     */
    interface Presenter {

        void onDestroy();

        void requestDataFromServer();

        void searchLocation(double latitude, double longitude);
    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetForecastInteractorImpl class
     **/
    interface MainView {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<ConsolidatedWeather> forecasts);

        void onResponseFailure(Throwable throwable);

    }

    interface Fragment {
        void setData(String location, ConsolidatedWeather consolidatedWeather);
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

        interface OnFinishedForecastListener {
            void onFinishedForecast(TreeMap<String, ConsolidatedWeather> locationSearch);

            void onFailedForecast(Throwable t);
        }

        void getForecastArrayList(int id, OnFinishedListener onFinishedListener);

        void getLocationSearch(double latitude, double longitude, OnFinishedSearchListener onFinishedListener);

        void get7daysForecast(int id, List<String> date, OnFinishedForecastListener onFinishedListener);

    }

}
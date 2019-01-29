package com.klaole.weatherapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.klaole.weatherapp.adapter.ForecastAdapter;
import com.klaole.weatherapp.adapter.ImageProvider;
import com.klaole.weatherapp.main_activity.DateProviderImpl;
import com.klaole.weatherapp.main_activity.GetForecastInteractorImpl;
import com.klaole.weatherapp.main_activity.MainContract;
import com.klaole.weatherapp.main_activity.MainPresenterImpl;
import com.klaole.weatherapp.models.ConsolidatedWeather;
import com.klaole.weatherapp.today_weather_fragment.TodaysWeatherFragment;
import com.klaole.weatherapp.util.ActivityUtils;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.MainView, TodaysWeatherFragment.OnFragmentInteractionListener {


    @BindView(R.id.recycler_view_forecast_list)
    RecyclerView recyclerView;

    private ProgressBar progressBar;
    private MainContract.Presenter presenter;

    private TodaysWeatherFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragmentView();
        initializeRecyclerView();
        initProgressBar();

        presenter = new MainPresenterImpl(new DateProviderImpl(MainActivity.this), this, fragment, new GetForecastInteractorImpl());
        presenter.requestDataFromServer();

    }


    private void initFragmentView() {
        fragment =
                (TodaysWeatherFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            // Create the fragment
            fragment = TodaysWeatherFragment.newInstance();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }
    }

    /**
     * Initializing Toolbar and RecyclerView
     */
    private void initializeRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

    }

    /**
     * Initializing progressbar programmatically
     */
    private void initProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        this.addContentView(relativeLayout, params);
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDataToRecyclerView(List<ConsolidatedWeather> forecasts) {
        ForecastAdapter adapter = new ForecastAdapter(new ImageProvider(this), forecasts);
        recyclerView.setAdapter(adapter);
        recyclerView.swapAdapter(adapter, true);
    }


    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(MainActivity.this,
                "Something went wrong...Error message: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

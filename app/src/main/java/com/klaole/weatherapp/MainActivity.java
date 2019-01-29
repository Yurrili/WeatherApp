package com.klaole.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.klaole.weatherapp.adapter.ForecastAdapter;
import com.klaole.weatherapp.adapter.ImageProvider;
import com.klaole.weatherapp.di.MyApp;
import com.klaole.weatherapp.main_activity.DateProvider;
import com.klaole.weatherapp.main_activity.MainContract;
import com.klaole.weatherapp.main_activity.MainPresenterImpl;
import com.klaole.weatherapp.models.ConsolidatedWeather;
import com.klaole.weatherapp.today_weather_fragment.TodaysWeatherFragment;
import com.klaole.weatherapp.util.ActivityUtils;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {


    @BindView(R.id.recycler_view_forecast_list)
    RecyclerView recyclerView;

    private ProgressBar progressBar;

    @Inject
    MainContract.GetForecastInteractor forecastInteractor;

    @Inject
    DateProvider dateProvider;

    private MainContract.Presenter presenter;

    private TodaysWeatherFragment fragment;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MyApp.app().forecastComponent().inject(this);

        initFragmentView();
        initializeRecyclerView();
        initProgressBar();
        presenter = new MainPresenterImpl(dateProvider, this, fragment, forecastInteractor);
        checkPermission();

    }


    private void initFragmentView() {
        fragment = (TodaysWeatherFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
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

    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_location_permission)
                .setMessage(R.string.text_location_permission)
                .setPositiveButton(R.string.ok, (dialogInterface, i) ->
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION))
                .create()
                .show();
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


    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showAlertDialog();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        } else {
            getLocation();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


    public void getLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            presenter.searchLocation(latitude, longitude);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();

                } else {
                    showAlertDialog();
                }
            }

        }
    }
}

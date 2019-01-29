package com.klaole.weatherapp;

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

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {


    @BindView(R.id.recycler_view_forecast_list)
    RecyclerView recyclerView;

    private ProgressBar progressBar;
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeRecyclerView();
        initProgressBar();
        presenter = new MainPresenterImpl(new DateProviderImpl(MainActivity.this), this, new GetForecastInteractorImpl());
        presenter.requestDataFromServer();

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


}

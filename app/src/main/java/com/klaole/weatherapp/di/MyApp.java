package com.klaole.weatherapp.di;


import android.app.Application;

public class MyApp extends Application {
    private static MyApp app;
    private ForecastComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        component = DaggerForecastComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static MyApp app() {
        return app;
    }

    public ForecastComponent forecastComponent() {
        return component;
    }
}

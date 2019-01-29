package com.klaole.weatherapp.di;

import com.klaole.weatherapp.MainActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = AppModule.class)
public interface ForecastComponent {
    void inject(MainActivity activity);

}

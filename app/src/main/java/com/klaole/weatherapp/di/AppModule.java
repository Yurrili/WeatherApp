package com.klaole.weatherapp.di;

import android.content.Context;

import com.klaole.weatherapp.main_activity.DateProvider;
import com.klaole.weatherapp.main_activity.DateProviderImpl;
import com.klaole.weatherapp.main_activity.GetForecastInteractorImpl;
import com.klaole.weatherapp.main_activity.MainContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    private Context context;

    public AppModule(Context ctx) {
        this.context = ctx;
    }


    @Singleton
    @Provides
    public MainContract.GetForecastInteractor provideGetForecastInteractor() {
        return new GetForecastInteractorImpl();
    }

    @Singleton
    @Provides
    public DateProvider provideDateProvider() {
        return new DateProviderImpl(context);
    }


}

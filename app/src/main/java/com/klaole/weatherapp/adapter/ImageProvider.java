package com.klaole.weatherapp.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.klaole.weatherapp.R;

import butterknife.BindDrawable;
import butterknife.ButterKnife;

public class ImageProvider {

    private static final String CLEAR = "c";
    private static final String LIGHT_CLOUD = "lc";
    private static final String HEAVY_CLOUD = "hc";
    private static final String SHOWERS = "s";
    private static final String LIGHT_RAIN = "lr";
    private static final String HEAVY_RAIN = "hr";
    private static final String THUNDERSTORM = "t";
    private static final String HAIL = "h";
    private static final String SLEET = "sl";
    private static final String SNOW = "sn";

    @BindDrawable(R.drawable.ic_c)
    Drawable c;
    @BindDrawable(R.drawable.ic_lc)
    Drawable lc;
    @BindDrawable(R.drawable.ic_hc)
    Drawable hc;
    @BindDrawable(R.drawable.ic_s)
    Drawable s;
    @BindDrawable(R.drawable.ic_lr)
    Drawable lr;
    @BindDrawable(R.drawable.ic_hr)
    Drawable hr;
    @BindDrawable(R.drawable.ic_t)
    Drawable t;
    @BindDrawable(R.drawable.ic_h)
    Drawable h;
    @BindDrawable(R.drawable.ic_sl)
    Drawable sl;
    @BindDrawable(R.drawable.ic_sn)
    Drawable sn;

    public ImageProvider(Activity ctx) {
        ButterKnife.bind(this, ctx);
        Log.d("ImageProvider", "Butterknife init");
    }

    public Drawable getWeatherIcon(String weatherState) {
        switch (weatherState) {
            case CLEAR:
                return c;
            case LIGHT_CLOUD:
                return lc;
            case HEAVY_CLOUD:
                return hc;
            case SHOWERS:
                return s;
            case LIGHT_RAIN:
                return lr;
            case HEAVY_RAIN:
                return hr;
            case THUNDERSTORM:
                return t;
            case HAIL:
                return h;
            case SLEET:
                return sl;
            case SNOW:
                return sn;
            default:
                return c;
        }
    }
}

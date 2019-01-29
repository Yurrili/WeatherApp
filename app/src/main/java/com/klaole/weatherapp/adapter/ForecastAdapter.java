package com.klaole.weatherapp.adapter;


import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.klaole.weatherapp.R;
import com.klaole.weatherapp.models.ConsolidatedWeather;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.WeatherViewHolder> {

    private List<ConsolidatedWeather> dataList;
    private ImageProvider imageProvider;

    public ForecastAdapter(ImageProvider imageProvider, List<ConsolidatedWeather> dataList) {
        this.imageProvider = imageProvider;
        this.dataList = dataList;
    }


    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_view_row, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txtWeatherDate.setText(dataList.get(position).getApplicableDate());
        Drawable drawable = imageProvider.getWeatherIcon(dataList.get(position).getWeatherStateAbbr());
        holder.imgWeatherIcon.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_weather_date)
        TextView txtWeatherDate;

        @BindView(R.id.iv_weather_icon)
        ImageView imgWeatherIcon;

        WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
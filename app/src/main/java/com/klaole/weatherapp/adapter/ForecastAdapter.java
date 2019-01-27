package com.klaole.weatherapp.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.klaole.weatherapp.R;
import com.klaole.weatherapp.models.ConsolidatedWeather;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.WeatherViewHolder> {

    private List<ConsolidatedWeather> dataList;

    public ForecastAdapter(List<ConsolidatedWeather> dataList) {
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
        holder.txtWeatherState.setText(dataList.get(position).getWeatherStateName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView txtWeatherState, txtDate;

        WeatherViewHolder(View itemView) {
            super(itemView);
            txtWeatherState = itemView.findViewById(R.id.tv_WeatherState);

        }
    }
}
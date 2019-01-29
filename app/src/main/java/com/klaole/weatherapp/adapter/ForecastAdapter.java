package com.klaole.weatherapp.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.klaole.weatherapp.R;
import com.klaole.weatherapp.models.ConsolidatedWeather;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
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
        holder.setTxtWeatherDate(dataList.get(position).getApplicableDate());
        holder.setTxtWeatherTemp(dataList.get(position).getTheTemp());
        holder.setImageWeatherState(dataList.get(position).getWeatherStateAbbr());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_weather_date)
        TextView txtWeatherDate;

        @BindView(R.id.tv_weather_temp)
        TextView txtWeatherTemp;

        @BindView(R.id.iv_weather_icon)
        ImageView imgWeatherIcon;

        @BindString(R.string.temp_formater)
        String tempFormat;

        WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void setTxtWeatherTemp(double temp) {
            txtWeatherTemp.setText(String.format(tempFormat, temp));
        }

        void setTxtWeatherDate(String date) {
            txtWeatherDate.setText(date);
        }

        void setImageWeatherState(String state) {
            imgWeatherIcon.setImageDrawable(imageProvider.getWeatherIcon(state));
        }
    }
}
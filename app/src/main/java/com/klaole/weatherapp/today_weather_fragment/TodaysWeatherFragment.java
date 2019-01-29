package com.klaole.weatherapp.today_weather_fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.klaole.weatherapp.R;
import com.klaole.weatherapp.adapter.ImageProvider;
import com.klaole.weatherapp.main_activity.MainContract;
import com.klaole.weatherapp.models.ConsolidatedWeather;

import androidx.fragment.app.Fragment;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class TodaysWeatherFragment extends Fragment implements MainContract.Fragment {

    @BindView(R.id.iv_main_weather_icon)
    ImageView imageView;

    @BindView(R.id.tv_temp)
    TextView tempTextView;

    @BindString(R.string.temp_formater)
    String tempFormat;

    private OnFragmentInteractionListener mListener;

    private ImageProvider imageProvider;

    public TodaysWeatherFragment() {
        // Required empty public constructor
    }

    public static TodaysWeatherFragment newInstance() {
        return new TodaysWeatherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageProvider = new ImageProvider(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todays_weather, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setData(ConsolidatedWeather consolidatedWeather) {
        Drawable drawable = imageProvider.getWeatherIcon(consolidatedWeather.getWeatherStateAbbr());
        imageView.setImageDrawable(drawable);
        tempTextView.setText(String.format(tempFormat, consolidatedWeather.getTheTemp()));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.example.android.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);

        // TODO (4) Delete the dummy weather data. You will be getting REAL data from the Internet in this lesson.

        // TODO (3) Delete the for loop that populates the TextView with dummy data

        // TODO (9) Call loadWeatherData to perform the network request to get the weather
        loadWeatherData();
    }

    // TODO (8) Create a method that will get the user's preferred location and execute your new AsyncTask and call it loadWeatherData
        public void loadWeatherData(){
            String location = SunshinePreferences.getPreferredWeatherLocation(this);
            new FetchWeatherTask().execute(location);
        }
    // TODO (5) Create a class that extends AsyncTask to perform network requests
    public class FetchWeatherTask extends AsyncTask<String, Void , String[]>{

        @Override
        protected String[] doInBackground(String... params) {
            String location = params[0];
            URL url = NetworkUtils.buildUrl(location);
            String getStringWeather = null;
            if(url==null) {
            return null;
            }
                try {
                    getStringWeather = NetworkUtils.getResponseFromHttpUrl(url);
                    String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                            .getSimpleWeatherStringsFromJson(MainActivity.this, getStringWeather);
                    return simpleJsonWeatherData;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

        }

        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);
            if(s.length!=0){
                for (String weatherString : s) {
                    mWeatherTextView.append((weatherString) + "\n\n\n");
                }
            }
        }
    }
    // TODO (6) Override the doInBackground method to perform your network requests
    // TODO (7) Override the onPostExecute method to display the results of the network request

}
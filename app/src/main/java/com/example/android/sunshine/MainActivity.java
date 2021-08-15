package com.example.android.sunshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView mWeatherTextView;
    private TextView mErrorTextView;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);
        mErrorTextView =findViewById(R.id.tv_error);
        mLoadingIndicator =findViewById(R.id.pb_loadingIndicator);

    }

        public void loadWeatherData(){
            String location = SunshinePreferences.getPreferredWeatherLocation(this);
            new FetchWeatherTask().execute(location);
        }

        public void showJsonData(){
        mErrorTextView.setVisibility(View.INVISIBLE);
        mWeatherTextView.setVisibility(View.VISIBLE);
        }

        public void showErrorMessage(){
            mErrorTextView.setVisibility(View.VISIBLE);
            mWeatherTextView.setVisibility(View.INVISIBLE);

        }

    public class FetchWeatherTask extends AsyncTask<String, Void , String[]>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);

        }

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

            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(s!= null){
                showJsonData();
                for (String weatherString : s) {
                    mWeatherTextView.append((weatherString) + "\n\n\n");
                }
            }
            else{
                showErrorMessage();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forecast , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int itemSelected = item.getItemId();
        if(itemSelected== R.id.action_refresh){
            mWeatherTextView.setText("");
            loadWeatherData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
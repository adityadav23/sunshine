package com.example.android.sunshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


    //Declaring

    private TextView mErrorTextView;
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;
    private ForecastAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        //Getting reference of RecyclerView and Adapter and LoadingIndicator
        mRecyclerView = findViewById(R.id.rv_weatherRecycler);
        mAdapter = new ForecastAdapter(this);
        mLoadingIndicator =findViewById(R.id.pb_loadingIndicator);
        mErrorTextView =findViewById(R.id.tv_error);


        //setting Adapter on recycler view
        mRecyclerView.setAdapter(mAdapter);

        //setting Layout Manager on Recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //setHasFixedSize true
        mRecyclerView.setHasFixedSize(true);


        //calling loadWeatherData
        loadWeatherData();
    }

        public void loadWeatherData(){
            String location = SunshinePreferences.getPreferredWeatherLocation(this);
            new FetchWeatherTask().execute(location);
        }

        public void showJsonData(){
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        }

        public void showErrorMessage(){
            mErrorTextView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);

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
                mAdapter.setWeatherData(s);
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
            loadWeatherData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
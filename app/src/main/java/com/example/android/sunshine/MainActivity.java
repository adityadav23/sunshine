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



    private TextView mErrorTextView;
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;
    private ForecastAdapter mAdapter;


// TODO (8) Implement ForecastAdapterOnClickHandler from the MainActivity
// TODO (11) Pass in 'this' as the ForecastAdapterOnClickHandler

    // TODO (9) Override ForecastAdapterOnClickHandler's onClick method
    // TODO (10) Show a Toast when an item is clicked, displaying that item's weather data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        // TODO (36) Delete the line where you get a reference to mWeatherTextView
        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */

        mRecyclerView = findViewById(R.id.rv_weatherRecycler);
        mAdapter = new ForecastAdapter(this);

       //setting Adapter on recycler view
        mRecyclerView.setAdapter(mAdapter);

        //setting Layout Manager on Recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //setHasFixedSize true
        mRecyclerView.setHasFixedSize(true);
        mErrorTextView =findViewById(R.id.tv_error);

        // TODO (38) Create layoutManager, a LinearLayoutManager with VERTICAL orientation and shouldReverseLayout == false

        // TODO (39) Set the layoutManager on mRecyclerView

        // TODO (40) Use setHasFixedSize(true) on mRecyclerView to designate that all items in
        //          the list will have the same size

        // TODO (41) set mForecastAdapter equal to a new ForecastAdapter

        // TODO (42) Use mRecyclerView.setAdapter and pass in mForecastAdapter

        mLoadingIndicator =findViewById(R.id.pb_loadingIndicator);

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
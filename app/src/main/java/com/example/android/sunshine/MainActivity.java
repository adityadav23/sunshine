package com.example.android.sunshine;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String[]> {


    //Declaring

    private TextView mErrorTextView;
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;
    private ForecastAdapter mAdapter;

    // Constant for Loader id
    private static final int WEATHER_LOADER =1;
    //CONSTANT to use as key value in bundle
    private static final String LOCATION_URL_EXTRA = null;


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


        //restarting loader
        getSupportLoaderManager().restartLoader(WEATHER_LOADER, null, this);
    }



    private void openLocationInMap() {
        // TODO (9) Use preferred location rather than a default location to display in the map
        String addressString = "1600 Ampitheatre Parkway, CA";
        Uri geoLocation = Uri.parse("geo:0,0?q=" + addressString);


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
          startActivity(intent);

    }


    public void showJsonData(){
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage(){
        mErrorTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);

    }


    @Override
    public Loader<String[]> onCreateLoader(int id,  Bundle args) {

        return new AsyncTaskLoader<String[]>(this) {
            String[] mWeatherData = null;


            @Override
            protected void onStartLoading() {
                if(mWeatherData!=null){
                    deliverResult(mWeatherData);
                }
                else{
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }


            @Override
            public String[] loadInBackground() {
                String location = SunshinePreferences.getPreferredWeatherLocation(MainActivity.this);
                URL locationUrl = NetworkUtils.buildUrl(location);

                String[] jsonWeather = null;
                try {   //convert string to url
                    String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(locationUrl);

                    jsonWeather = OpenWeatherJsonUtils
                            .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);
                }catch(IOException e){
                    e.printStackTrace();
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }

                return jsonWeather;
            }

            @Override
            public void deliverResult( String[] data) {
                mWeatherData= data;
                super.deliverResult(data);
            }
        };

    }



    @Override
    public void onLoadFinished( Loader<String[]> loader, String[] data) {

        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if(data!= null){
            showJsonData();
            mAdapter.setWeatherData(data);
        }
        else{
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset( Loader<String[]> loader) {

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
            getSupportLoaderManager().restartLoader(WEATHER_LOADER, null, this);
            return true;
        }else if(itemSelected == R.id.action_settings){
            Intent intent = new Intent(MainActivity.this , SettingsActivity.class);
            startActivity(intent);
            return true;
        }else if(itemSelected==R.id.action_map){
            openLocationInMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
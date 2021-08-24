package com.example.android.sunshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #AdityaSunshineApp";
    private TextView mDisplayWeatherOfDay;
    public String mForecast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Reference of textView
        mDisplayWeatherOfDay = findViewById(R.id.tv_weatherOfDay);
        //getting intent and the the string value
        Intent intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT))
        {
            mForecast = intent.getStringExtra(Intent.EXTRA_TEXT);
            mDisplayWeatherOfDay.setText(mForecast);
        }




    }
    private Intent createShareForecastIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mForecast + FORECAST_SHARE_HASHTAG)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
      int itemSelected = item.getItemId();
      if(itemSelected== R.id.action_share){
          setIntent(createShareForecastIntent());

      }else if(itemSelected== R.id.action_details_settings){
          Intent intent = new Intent(this, SettingsActivity.class);
          startActivity(intent);
          return true;
      }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.android.sunshine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    //Initializing
    private String[] mWeatherData;

    private Context context;

    //Adapter
    public ForecastAdapter(Context context ){
        this.context = context;
    }

    //  Override onCreateViewHolder
    //  Within onCreateViewHolder, inflate the list item xml into a view
    //  Within onCreateViewHolder, return a new ForecastAdapterViewHolder
    //  with the above view passed in as a parameter



    @Override
    public ForecastAdapterViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_list_item, parent,
                false);
        //passing this view to ViewHolder constructor
        ForecastAdapterViewHolder viewHolder = new ForecastAdapterViewHolder(view);
        return viewHolder;

    }


    // Override onBindViewHolder
    // Set the text of the TextView to the weather for this list item's position


    @Override
    public void onBindViewHolder( ForecastAdapterViewHolder holder, int position) {

        String weather = mWeatherData[position];
        holder.mWeatherTextView.setText(weather);

    }
    // TODO (29) Override getItemCount
    // TODO (30) Return 0 if mWeatherData is null, or the size of mWeatherData if it is not null


    @Override
    public int getItemCount() {
        if(mWeatherData == null ){
            return 0;
        }
        return  mWeatherData.length;
    }


    // TODO (18) Create a public final TextView variable called mWeatherTextView

    // TODO (19) Create a constructor for this class that accepts a View as a parameter
    // TODO (20) Call super(view) within the constructor for ForecastAdapterViewHolder
    // TODO (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //initializing the text
        private  TextView mWeatherTextView;

        public ForecastAdapterViewHolder( View itemView) {
            super(itemView);
            mWeatherTextView = itemView.findViewById(R.id.tv_weather_data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String weatherOfDay = mWeatherData[getAdapterPosition()];
            Intent intent = new Intent(view.getContext(),DetailActivity.class);
            //passing weather to the intent
            intent.putExtra(Intent.EXTRA_TEXT , weatherOfDay);
            view.getContext().startActivity(intent);
        }
    }
    //  Create a setWeatherData method that saves the weatherData to mWeatherData
    //  After you save mWeatherData, call notifyDataSetChanged
    public void setWeatherData(String[] weatherData) {
        mWeatherData = weatherData;
        notifyDataSetChanged();
    }
}

package com.example.android.sunshine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {
    private String[] mWeatherData;

    private Context context;
    // TODO (3) Create a final private ForecastAdapterOnClickHandler called mClickHandler

    // TODO (1) Add an interface called ForecastAdapterOnClickHandler
    // TODO (2) Within that interface, define a void method that access a String as a parameter

    // TODO (4) Add a ForecastAdapterOnClickHandler as a parameter to the constructor and store it in mClickHandler

    // TODO (5) Implement OnClickListener in the ForecastAdapterViewHolder class
    // TODO (6) Override onClick, passing the clicked day's data to mClickHandler via its onClick method
    public ForecastAdapter(Context context ){
        this.context = context;

    }

    // TODO (24) Override onCreateViewHolder
    // TODO (25) Within onCreateViewHolder, inflate the list item xml into a view
    // TODO (26) Within onCreateViewHolder, return a new ForecastAdapterViewHolder
    //           with the above view passed in as a parameter



    @Override
    public ForecastAdapterViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_list_item, parent,
                false);
        //passing this view to ViewHolder constructor
        ForecastAdapterViewHolder viewHolder = new ForecastAdapterViewHolder(view);
        return viewHolder;

    }


    // TODO (27) Override onBindViewHolder
    // TODO (28) Set the text of the TextView to the weather for this list item's position


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



    // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////////
    // TODO (18) Create a public final TextView variable called mWeatherTextView

    // TODO (19) Create a constructor for this class that accepts a View as a parameter
    // TODO (20) Call super(view) within the constructor for ForecastAdapterViewHolder
    // TODO (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView

 public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private  TextView mWeatherTextView;
     public ForecastAdapterViewHolder( View itemView) {
         super(itemView);
         mWeatherTextView = itemView.findViewById(R.id.tv_weather_data);
         itemView.setOnClickListener(this);
     }

     @Override
     public void onClick(View view) {
         int adapterPosition = getAdapterPosition();
         String weatherForDay = mWeatherData[adapterPosition];
         Toast.makeText(view.getContext(),"position"+mWeatherData[adapterPosition] +" "+ weatherForDay,
                        Toast.LENGTH_LONG).show();
     }
 }
    // TODO (31) Create a setWeatherData method that saves the weatherData to mWeatherData
    // TODO (32) After you save mWeatherData, call notifyDataSetChanged
    public void setWeatherData(String[] weatherData) {
        mWeatherData = weatherData;
        notifyDataSetChanged();
    }
   }

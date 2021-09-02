package com.example.android.sunshine.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class WeatherContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.sunshine";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);

    public static final String PATH_WEATHER = "weather";

    public static final class WeatherEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WEATHER)
                .build();

        public static final String TABLE_NAME = "weather";


        public static final String COLUMN_DATE = "date";

        /* Weather ID as returned by API, used to identify the icon to be used */
        public static final String COLUMN_WEATHER_ID = "weather_id";

        /* Min and max temperatures in °C for the day (stored as floats in the database) */
        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";

        /* Humidity is stored as a float representing percentage */
        public static final String COLUMN_HUMIDITY = "humidity";

        /* Pressure is stored as a float representing percentage */
        public static final String COLUMN_PRESSURE = "pressure";

        /* Wind speed is stored as a float representing wind speed in mph */
        public static final String COLUMN_WIND_SPEED = "wind";

        /*
         * Degrees are meteorological degrees (e.g, 0 is north, 180 is south).
         * Stored as floats in the database.
         *
         * Note: These degrees are not to be confused with temperature degrees of the weather.
         */
        public static final String COLUMN_DEGREES = "degrees";

    }
}

package com.example.android.sunshine.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WeatherProvider extends ContentProvider {


    public static final int CODE_WEATHER = 100;
    public static final int CODE_WEATHER_WITH_DATE =101;

        private static final UriMatcher sUriMatcher = buildUriMatcher();
        private WeatherDbHelper mOpenHelper;

        private static UriMatcher buildUriMatcher(){
            final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
            final String authority = WeatherContract.CONTENT_AUTHORITY;



            matcher.addURI(authority, WeatherContract.PATH_WEATHER, CODE_WEATHER);
            matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/#", CODE_WEATHER_WITH_DATE);

            return matcher;

        }



    @Override
    public boolean onCreate() {

        mOpenHelper = new WeatherDbHelper(getContext());
        return true;
            }


        @Override
        public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                String[] selectionArgs, String sortOrder) {

            Cursor cursor;


            switch (sUriMatcher.match(uri)) {


                case CODE_WEATHER_WITH_DATE: {


                    String normalizedUtcDateString = uri.getLastPathSegment();

                    String[] selectionArguments = new String[]{normalizedUtcDateString};

                    cursor = mOpenHelper.getReadableDatabase().query(
                            /* Table we are going to query */
                            WeatherContract.WeatherEntry.TABLE_NAME,

                            projection,

                            WeatherContract.WeatherEntry.COLUMN_DATE + " = ? ",
                            selectionArguments,
                            null,
                            null,
                            sortOrder);

                    break;
                }


                case CODE_WEATHER: {
                    cursor = mOpenHelper.getReadableDatabase().query(
                            WeatherContract.WeatherEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);

                    break;
                }

                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }


    @Override
    public String getType(@NonNull Uri uri) {

            throw new RuntimeException("This function getType is not implemented");
    }


    @Override
    public Uri insert(@NonNull Uri uri,  ContentValues contentValues) {
        throw new RuntimeException(" We are not implementing insert in Sunshine. Use bulkInsert instead");

    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

       return 0;
    }


    @Override
    public int update(@NonNull Uri uri,  ContentValues contentValues, String s,  String[] strings) {
        throw new RuntimeException("We are not implementing update in Sunshine");
    }
}

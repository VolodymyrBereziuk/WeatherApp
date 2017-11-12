package vb.com.weatherapp.weather_datails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import vb.com.weatherapp.R


class WeatherDetailsActivity : AppCompatActivity(){
    val TAG = "WeatherDetailsActivity"
    val DETAILS_BUNDLE = "DETAILS_BUNDLE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)

        var weatherObject = intent.extras.get(DETAILS_BUNDLE)

        Log.e(TAG, weatherObject.toString())

    }
}
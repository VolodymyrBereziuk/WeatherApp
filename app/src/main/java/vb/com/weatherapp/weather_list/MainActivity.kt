package vb.com.weatherapp.weather_list

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import okhttp3.*
import java.io.IOException
import org.json.JSONObject
import vb.com.weatherapp.R
import vb.com.weatherapp.weather_datails.WeatherDetailsActivity
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    val client = OkHttpClient()
    val DETAILS_BUNDLE = "DETAILS_BUNDLE"

    data class WeatherObject(var temp: Double, var timestamp: Long) : Serializable

    var weatherList = ArrayList<WeatherObject>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val key = "&appid=c14012ba445a231bd1bd836b6ebf09ad&units=metric"
        val city = "Lviv"
        val baseUrl = "https://api.openweathermap.org/data/2.5/forecast?q="

        initView(weatherList)
        run(baseUrl, city, key)

    }

    fun initView(list: ArrayList<WeatherObject>) {
        var weatherAdapter = findViewById<RecyclerView>(R.id.weather_list)
        weatherAdapter.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        weatherAdapter.addItemDecoration(LinearLayoutSpaceItemDecoration(4))
        weatherAdapter.adapter = WeatherAdapter(list) {
            Log.e(TAG, "${it.temp} clicked")
            var intent = Intent(this, WeatherDetailsActivity::class.java)
            intent.putExtra(DETAILS_BUNDLE, it)
            this.startActivity(intent)

        }

    }

    fun run(url: String, city: String, api: String) {

        val apiUrl = url + city + api
        Log.e(TAG, apiUrl)

        val request = Request.Builder()
                .url(apiUrl)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                var responseString = response.body()?.string()
                parseJSON(responseString)
            }

        })

    }

    private fun parseJSON(json: String?) {
        val jsonObj = JSONObject(json)
        Log.e(TAG, jsonObj.toString())
        val jsonList = jsonObj.getJSONArray("list")
        for (i in 0 until jsonList!!.length() - 1) {

            var weatherObject = WeatherObject(
                    jsonList.getJSONObject(i).getJSONObject("main").getDouble("temp"),
                    jsonList.getJSONObject(i).getLong("dt")
            )
            Log.e(TAG, weatherObject.toString())
            weatherList.add(weatherObject)
        }
        runOnUiThread {
            Log.e(TAG, " runOnUiThread ")
            initView(weatherList)
        }

    }


}

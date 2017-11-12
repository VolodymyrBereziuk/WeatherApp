package vb.com.weatherapp.weather_list


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.*
import vb.com.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*

class WeatherAdapter(val weatherList:  ArrayList<MainActivity.WeatherObject>, val listener: (MainActivity.WeatherObject) -> Unit) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(weatherList[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(weatherObject: MainActivity.WeatherObject, listener: (MainActivity.WeatherObject) -> Unit)  = with(itemView) {

            itemView.text_temperature.text = weatherObject.temp.toString() + "  °C"

            val sdf = SimpleDateFormat("EEEE, MMM d, HH:mm", Locale.ENGLISH)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = weatherObject.timestamp * 1000
            itemView.text_date.text = sdf.format(calendar.time)

            itemView.setOnClickListener { listener(weatherObject) }
        }
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }
}
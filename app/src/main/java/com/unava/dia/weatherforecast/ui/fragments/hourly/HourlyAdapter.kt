package com.unava.dia.weatherforecast.ui.fragments.hourly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.unava.dia.weatherforecast.R
import com.unava.dia.weatherforecast.data.model.future.Hour
import com.unava.dia.weatherforecast.utils.GlideUtil
import com.unava.dia.weatherforecast.utils.HourDiffUtil
import com.unava.dia.weatherforecast.utils.getDateString
import java.text.SimpleDateFormat
import java.util.*

class HourlyAdapter(private val response: MutableList<Hour>): RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_item, parent, false)
        return HourlyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val item = getItem(position)

        holder.hour.text = getDateString(item.time_epoch!!)
        val tvCondition = item.condition?.icon
        holder.weather.let { GlideUtil.setImage(it, "https:${tvCondition}") }
        (item.temp_c.toString() + "°C").also { holder.temp.text = it }
        ("rain: " + item.chance_of_rain.toString() + "%").also { holder.rainChance.text = it }
        ("wind kph:" + item.wind_kph.toString()).also { holder.wind.text = it }
        ("humidity" + item.humidity.toString() + "% ").also { holder.humidity.text = it }
    }

    fun addWeather(newWeather: List<Hour>) {
        val wDiffUtil = HourDiffUtil(response, newWeather)
        val diffResult = DiffUtil.calculateDiff(wDiffUtil)
        response.clear()
        response.addAll(newWeather)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getItem(position: Int): Hour {
        return response[position]
    }

    class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val hour: TextView = itemView.findViewById(R.id.tvHour)
        val weather: ImageView = itemView.findViewById(R.id.ivWeather)
        val temp: TextView = itemView.findViewById(R.id.tvTempHour)
        val rainChance: TextView = itemView.findViewById(R.id.tvRainChance)
        val wind: TextView = itemView.findViewById(R.id.tvWind)
        val humidity: TextView = itemView.findViewById(R.id.tvPressure)
    }

    override fun getItemCount(): Int {
        return response.size
    }
}
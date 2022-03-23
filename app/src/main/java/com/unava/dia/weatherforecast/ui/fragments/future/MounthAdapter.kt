package com.unava.dia.weatherforecast.ui.fragments.future

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.unava.dia.weatherforecast.R
import com.unava.dia.weatherforecast.data.model.future.Forecastday
import com.unava.dia.weatherforecast.utils.DayDiffUtil
import com.unava.dia.weatherforecast.utils.GlideUtil

class MounthAdapter(private val response: MutableList<Forecastday>)  :
    RecyclerView.Adapter<MounthAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mounth_item, parent, false)
        view.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        return CustomViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val day = getItem(position)

        holder.date.text = day.date.toString()

        val tvCondition = day.day?.condition?.icon
        holder.ivCondition.let { GlideUtil.setImage(it, "https:${tvCondition}") }

        holder.day.text = "${day.day?.maxtemp_c.toString()}°C"
        holder.night.text = "${day.day?.mintemp_c.toString()}°C"

        val avg: Int = day.day?.avgtemp_c?.toInt()!!

        when {
            avg < 0 -> holder.lMounth.setBackgroundColor(Color.parseColor("#edfaff"))
            avg == 0 -> holder.lMounth.setBackgroundColor(Color.parseColor("#f1fbff"))
            avg in 1..6 -> holder.lMounth.setBackgroundColor(Color.parseColor("#f9fdff"))
            avg in 8..9 -> holder.lMounth.setBackgroundColor(Color.parseColor("#f8fdf4"))
            avg in 11..13 -> holder.lMounth.setBackgroundColor(Color.parseColor("#f1fbe6"))
        }
    }

    override fun getItemCount(): Int {
        return response.size
    }

    fun getItem(position: Int): Forecastday {
        return response[position]
    }

    fun addWeather(newWeather: List<Forecastday>) {
        val wDiffUtil = DayDiffUtil(response, newWeather)
        val diffResult = DiffUtil.calculateDiff(wDiffUtil)
        response.clear()
        response.addAll(newWeather)
        diffResult.dispatchUpdatesTo(this)
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val ivCondition: ImageView = itemView.findViewById(R.id.ivCond)
        val day: TextView = itemView.findViewById(R.id.tvDayTemp)
        val night: TextView = itemView.findViewById(R.id.tvNightTemp)
        val lMounth: LinearLayoutCompat = itemView.findViewById(R.id.lMounth)

    }
}
package com.unava.dia.weatherforecast.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.unava.dia.weatherforecast.data.model.future.Hour

class HourDiffUtil(private var oldList: List<Hour>, private var newList: List<Hour>)
    : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].time === newList[newItemPosition].time &&
                oldList[oldItemPosition].temp_c === newList[newItemPosition].temp_c &&
                oldList[oldItemPosition].gust_kph === newList[newItemPosition].gust_kph
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition] &&
                oldList[oldItemPosition].time === newList[newItemPosition].time &&
                oldList[oldItemPosition].temp_c === newList[newItemPosition].temp_c &&
                oldList[oldItemPosition].gust_kph === newList[newItemPosition].gust_kph
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
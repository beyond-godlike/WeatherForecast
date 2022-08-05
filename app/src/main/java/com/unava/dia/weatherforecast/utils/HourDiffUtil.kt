package com.unava.dia.weatherforecast.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.unava.dia.weatherforecast.data.model.future.Hour

class HourDiffUtil(internal var oldList: List<Hour>, internal var newList: List<Hour>)
    : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].time_epoch === newList[newItemPosition].time_epoch
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].equals(newList[newItemPosition])
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
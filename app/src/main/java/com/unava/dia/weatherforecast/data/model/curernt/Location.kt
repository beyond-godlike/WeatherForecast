package com.unava.dia.weatherforecast.data.model.curernt

data class Location(
    var name: String? = null,
    var region: String? = null,
    var country: String? = null,
    var lat: Double? = null,
    var lon: Double? = null,
    var tz_id: String? = null,
    var localtime_epoch: Double? = null,
    var localtime: String? = null,
)
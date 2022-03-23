package com.unava.dia.weatherforecast.data.model.future

data class Forecastday (
    var date : String? = null,
    var date_epoch : Int? = null,
    var day : Day? = null,
    var astro : Astro? = null,
    var hour : List<Hour>? = null
)
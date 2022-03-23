package com.unava.dia.weatherforecast.data.model.future

data class Day (
    var maxtemp_c : Double? = null,
    var maxtemp_f : Double? = null,
    var mintemp_c : Double? = null,
    var mintemp_f : Double? = null,
    var avgtemp_c : Double? = null,
    var avgtemp_f : Double? = null,
    var maxwind_mph : Double? = null,
    var maxwind_kph : Double? = null,
    var totalprecip_mm : Double? = null,
    var totalprecip_in : Double? = null,
    var avgvis_km :Double? = null,
    var avgvis_miles : Double? = null,
    var avghumidity : Double? = null,
    var daily_will_it_rain : Double? = null,
    var daily_chance_of_rain : Double? = null,
    var daily_will_it_snow : Double? = null,
    var daily_chance_of_snow : Double? = null,
    var condition : Condition? = null,
    var uv : Double? = null
)
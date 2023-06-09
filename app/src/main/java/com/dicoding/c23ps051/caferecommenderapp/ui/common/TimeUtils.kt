package com.dicoding.c23ps051.caferecommenderapp.ui.common

import android.os.Build
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.TimeZone

fun isOpen(openingHour: Int, closingHour: Int): Boolean {
    val time = getJakartaTime()
    return (time in openingHour until closingHour)
}

private fun getJakartaTime(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val jakartaZoneId = ZoneId.of("Asia/Jakarta")
        LocalDateTime.now(jakartaZoneId).hour
    } else {
        val jakartaTimeZone = TimeZone.getTimeZone("Asia/Jakarta")
        Calendar.getInstance(jakartaTimeZone).get(Calendar.HOUR_OF_DAY)
    }
}
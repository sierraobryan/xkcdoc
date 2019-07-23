package com.example.sierraobryan.xkcdocument.utils

import java.lang.StringBuilder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun withinLastThreeDays(day: String, month: String, year: String) : Boolean {
    try {
        val comicDate = StringBuilder(day).append("/").append(month).append("/").append(year).toString()
        val date : Date = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(comicDate)
        val currentDate: Calendar = Calendar.getInstance()
        val currentDateMinusThree: Calendar = Calendar.getInstance()
        currentDateMinusThree.add(Calendar.DAY_OF_YEAR, -7)
        return !(date.before(currentDateMinusThree.time as Date) || date.after(currentDate.time as Date))
    } catch (e : ParseException) {
        return false
    }
}
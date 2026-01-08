package com.muhammad.pilltime.utils

import android.annotation.SuppressLint
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.daysUntil
import kotlinx.datetime.minus
import kotlinx.datetime.plus

fun getWeekDatesFrom(date : LocalDate) : List<LocalDate>{
    val startOfWeek = date.minus(date.dayOfWeek.ordinal, DateTimeUnit.DAY)
    return (0..6).map { day -> startOfWeek.plus(day, DateTimeUnit.DAY) }
}

@SuppressLint("DefaultLocale")
fun LocalTime.formattedTime() : String{
    val hour24 = this.hour
    val hour = when{
        hour24 == 0 -> 12
        hour24 > 12 -> hour24 - 12
        else -> hour24
    }
    val minute = this.minute
    val amPm = if(hour24 < 12) "AM" else "PM"
    return String.format("%02d:%02d %s", hour, minute,amPm)
}

fun LocalDate.formattedDuration(otherDate : LocalDate) : String{
    var start = this
    var end = otherDate
    if(start > end){
        val temp = start
        start = end
        end = temp
    }
    var years = 0
    var months = 0

    while(start.plus(1, DateTimeUnit.YEAR) <= end){
        start = start.plus(1, DateTimeUnit.YEAR)
        years++
    }
    while(start.plus(1, DateTimeUnit.MONTH) <= end){
        start = start.plus(1, DateTimeUnit.MONTH)
        months++
    }
    val days = start.daysUntil(end)
    val parts = buildList {
        if(years > 0) add("$years ${if(years == 1) "year" else "years"}")
        if(months > 0) add("$months ${if(months == 1) "month" else "months"}")
        if(days > 0 || isEmpty()) add("$days ${if(days == 1) "day" else "days"}")
    }
    return parts.joinToString(" ")
}

fun LocalDate.formattedFullDuration(otherDate : LocalDate) : String{
    val startDate = this
    val endDate = otherDate
    val startDay = startDate.day
    val endDay = endDate.day
    val startMonth = startDate.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
    val endMonth = endDate.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
    val startYear = startDate.year
    val endYear = endDate.year
    val isSameYear = startYear == endYear
    return "$startDay $startMonth${if(isSameYear) "" else " $startYear"} - $endDay $endMonth $endYear"
}
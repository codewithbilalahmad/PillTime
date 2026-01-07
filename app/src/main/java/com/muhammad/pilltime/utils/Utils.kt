package com.muhammad.pilltime.utils

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

fun getWeekDatesFrom(date : LocalDate) : List<LocalDate>{
    val startOfWeek = date.minus(date.dayOfWeek.ordinal, DateTimeUnit.DAY)
    return (0..6).map { day -> startOfWeek.plus(day, DateTimeUnit.DAY) }
}
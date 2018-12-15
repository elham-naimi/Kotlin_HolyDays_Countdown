package com.elna.util

import android.util.Log
import com.elna.db.HolyDaysForGivenYear
import com.elna.model.HolyDay
import org.json.JSONArray
import org.json.JSONObject
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import org.threeten.bp.temporal.ChronoUnit.DAYS
import org.threeten.bp.temporal.ChronoUnit.MINUTES


val BICENTENARY_MINUTE = 0
val NEXT_UPDATE_MINUTE = 0

val BICENTENARY_HOUR = 18
val NEXT_UPDATE_HOUR = 18

val BICENTENARY_DAY = 28
val BICENTENARY_MONTH = 10

val BICENTENARY_YEAR = 2019
val NEXT_UPDATE_SECOND = 0

public fun getCurrentTime(): Calendar {
    return Calendar.getInstance()
}

fun getDaysLeft(bicentenaryHolyDay: LocalDateTime?): Long {
    if (bicentenaryHolyDay == null) return -1
    val today = LocalDateTime.now()
    var days = DAYS.between(today, bicentenaryHolyDay)
    val minutes = MINUTES.between(today.plusDays(days), bicentenaryHolyDay)
    if (minutes > 1)
        days++
    if (days < 0) days = 0
    return days
}

private fun getBicentenaryHolyDate(): LocalDateTime {
    return LocalDateTime.of(BICENTENARY_YEAR, BICENTENARY_MONTH, BICENTENARY_DAY,
            BICENTENARY_HOUR, BICENTENARY_MINUTE, 0)
}

fun getLastHolyDayOfTheYear(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.MONTH, Calendar.NOVEMBER)
    calendar.set(Calendar.DAY_OF_MONTH, 28)
    calendar.set(Calendar.HOUR_OF_DAY, 18)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    return calendar
}

fun getNextYear(): Int {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.YEAR, 1)
    return calendar.get(Calendar.YEAR)
}

fun getCurrentYear(): Int {
    return Calendar.getInstance().get(Calendar.YEAR)
}





fun parseHolyDays(data: HolyDaysForGivenYear, year: Int): ArrayList<HolyDay> {

    var holyDays = ArrayList<HolyDay>()
    var jsonObject = JSONArray(data.holyDays)
    for( i in 0 until jsonObject.length() ){
        val formatter = DateTimeFormatter.ofPattern("MMMMddyyyyHHmmss")
        val date : LocalDateTime = LocalDateTime.parse(((jsonObject.get(i) as JSONObject).get("holyDayWhen") as CharSequence?), formatter)
        holyDays.add(HolyDay((jsonObject.get(i) as JSONObject).get("holyDayName") as String, date))
    }
    return  holyDays;
}

/*

the value 0 if the time represented by the argument is equal to the time represented by this Calendar;
 a value less than 0 if the time of this Calendar is before the time represented by the argument;
 and a value greater than 0 if the time of this Calendar is after the time represented by the argument.
 */



/*
fun getUpcomingHolyDayIndex(): Int {
    var holyDays: ArrayList<HolyDay>? = null
    val currentDate = getCurrentTime()
    if (currentDate.compareTo(getLastHolyDayOfTheYear()) < 0) {
        holyDays = parseHolyDays(getHolyDaysStringForYear(getCurrentYear()),getCurrentYear())

    } else {

        holyDays = parseHolyDays(getHolyDaysStringForYear(getNextYear()), getNextYear())

    }

    return getUpcomingHolyDayIndex(holyDays!!)

}


/*
fun getHolyDaysStringForYear(year: Int): String {
    return HolyDaysData.holyDays.get(year)
}



fun getUpcomingHolyDayIndex(dateList: ArrayList<HolyDay>): Int {
    val currentDate = LocalDateTime.now()

    var index = 0
    var flag = false
    for (holyDay in dateList) {

        //    LocalDateTime endOfHolyDay =  holyDay.date.plusDays(1);

        if (currentDate.isBefore(holyDay.date)) {
            flag = true
            break
        }
        index++
    }
    return index

}


fun getUpcomingHolyDays(numberOfHolyDaysToShow: Int): ArrayList<HolyDay> {
    val upcomingHolyDays = ArrayList<HolyDay>()
    val holyDaysList = ArrayList<HolyDay>()
    var index = 0

    // Current year + Next year
    if (Util.getCurrentTime().compareTo(Util.getLastHolyDayOfTheYear()) < 0) {
        index = Util.getUpcomingHolyDayIndex()
        val currentYearHolyDays = Util.parseHolyDays(Util.getHolyDaysStringForYear(Util.getCurrentYear()), Util.getCurrentYear())
        val nextYearHolyDays = Util.parseHolyDays(Util.getHolyDaysStringForYear(Util.getNextYear()), Util.getNextYear())
        holyDaysList.addAll(currentYearHolyDays)
        holyDaysList.addAll(nextYearHolyDays)
    } else {
        val nextYearHolyDays = Util.parseHolyDays(Util.getHolyDaysStringForYear(Util.getNextYear()), Util.getNextYear())
        holyDaysList.addAll(nextYearHolyDays)
    }// Reached end of year. Add only Next year

    // Population to another list. Can be removed.
    for (i in 0 until numberOfHolyDaysToShow) {
        try {
            upcomingHolyDays.add(holyDaysList[index])
            index++
        } catch (e: IndexOutOfBoundsException) {
            upcomingHolyDays.add(HolyDay(LocalDateTime.now(), "Year currently not supported"))
        }

    }
    return upcomingHolyDays
}
}

*/
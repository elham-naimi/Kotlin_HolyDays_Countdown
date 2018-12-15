package com.elna.db

import io.reactivex.Flowable
import org.json.JSONArray
import org.json.JSONObject
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import android.util.Log
import com.elna.model.HolyDay
import com.elna.util.getCurrentYear
import com.elna.util.getNextYear
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers


class HolyDayRepository(private val holDayDao: HolyDaysForGivenYearDao) {

    fun insert(holyDays: HolyDaysForGivenYear) {
        holDayDao.insert(holyDays)
    }


    private fun queryCurrentYear(): Flowable<HolyDaysForGivenYear>? {
        return holDayDao.getHolyDaysForYear(getCurrentYear()).subscribeOn(Schedulers.io())
    }

    private fun queryNextYear(): Flowable<HolyDaysForGivenYear>? {
        return holDayDao.getHolyDaysForYear(getNextYear()).subscribeOn(Schedulers.io())
    }

    fun queryUpcomingHolidays() : Flowable<ArrayList<HolyDay>>{
       return  Flowable.zip(queryCurrentYear(), queryNextYear(), BiFunction<HolyDaysForGivenYear, HolyDaysForGivenYear, ArrayList<HolyDay>> { data1, data2 ->

            var list = ArrayList<HolyDaysForGivenYear>()
            list.add(data1)
            list.add(data2)
             getHolyDays(list)
       })
    }


    private fun getHolyDays(data: ArrayList<HolyDaysForGivenYear>): ArrayList<HolyDay> {

        var holyDays = ArrayList<HolyDay>()
        for (i in 0 until data.size) {
            var jsonObject = JSONArray(data.get(i).holyDays)
            Log.i("DB", "Object length " + jsonObject.length())
            for (i in 0 until jsonObject.length()) {
                val formatter = DateTimeFormatter.ofPattern("MMMMddyyyyHHmmss")
                val date: LocalDateTime = LocalDateTime.parse(((jsonObject.get(i) as JSONObject).get("holyDayWhen") as CharSequence?), formatter)
                holyDays.add(HolyDay((jsonObject.get(i) as JSONObject).get("holyDayName") as String, date))
            }
        }

        return  holyDays;
    }
}




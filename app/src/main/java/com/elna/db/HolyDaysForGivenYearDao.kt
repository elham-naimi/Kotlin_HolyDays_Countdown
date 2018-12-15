package com.elna.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.elna.db.HolyDaysForGivenYear
import io.reactivex.Flowable


@Dao
interface HolyDaysForGivenYearDao {

    // Gets all people in the database
    @Query("SELECT * FROM my_table where year= :year")
    fun getHolyDaysForYear(year : Int): Flowable<HolyDaysForGivenYear>

    // Adds a person to the database
    @Insert
    fun insert(holyDays : HolyDaysForGivenYear)

}
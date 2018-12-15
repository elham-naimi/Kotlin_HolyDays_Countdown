package com.elna.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.elna.db.HolyDayRepository
import com.elna.model.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.elna.db.HolyDaysDatabase
import io.reactivex.Flowable
import io.reactivex.Single
import kotlin.collections.ArrayList

public class HolyDayViewModel(private val app: Application) : AndroidViewModel(app) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    private lateinit var repository : HolyDayRepository

    fun loadHolyDays(): Flowable<ArrayList<HolyDay>> {
         val wordsDao = HolyDaysDatabase.getDatabase(app, scope).holyDayDao()
         repository = HolyDayRepository(wordsDao)
         return repository.queryUpcomingHolidays()
     }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}

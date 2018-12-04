package com.elna.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.elna.model.HolyDay
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

public class HolyDayViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    val holyDayList : ArrayList<HolyDay>


    init {
          holyDayList = getHolyDays()
    }

    private fun getHolyDays(): ArrayList<HolyDay> {


        var list = ArrayList<HolyDay>()
        list.add(nawRuz)
        return  list
    }


    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}

package com.elna.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.util.Log
import com.elna.kotlinfragment.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

@Database(entities = arrayOf(HolyDaysForGivenYear::class), version = 1)

@TypeConverters(Converters::class)
public abstract class HolyDaysDatabase : RoomDatabase() {

    abstract fun holyDayDao(): HolyDaysForGivenYearDao



    companion object {
        @Volatile
        private var INSTANCE: HolyDaysDatabase? = null

        fun getDatabase(context: Context, scope : CoroutineScope): HolyDaysDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            Log.i("DB","Creating database")
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        HolyDaysDatabase::class.java,
                        "holyDays_database"
                ).addCallback(HolyDayDatabaseCallback(scope, context))
                        .build()
                INSTANCE = instance
                Log.i("DB","Created database :  returning instance")
                return instance
            }
        }
    }
    private class HolyDayDatabaseCallback(
            private val scope: CoroutineScope, val context: Context
    ) : RoomDatabase.Callback() {


        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.i("DB","Database callback :: onCreate :: start")

            INSTANCE?.let {
                database ->
              scope.launch(Dispatchers.IO) {
                     Log.i("DB","populateDatabase start")
                    populateDatabase(database.holyDayDao())
                Log.i("DB","populateDatabase finish")
               }
            }
            Log.i("DB","Database callback :: onCreate :: finish")
        }

        private fun populateDatabase(holyDayDao: HolyDaysForGivenYearDao) {

             var text  = context.applicationContext.resources.openRawResource(R.raw.holyday)
                    .bufferedReader().use { it.readText() }
             var array  : JSONArray = JSONObject(text).get("years") as JSONArray

             Log.i("TAG","length "+array.length())
             var holyDaysForGivenYear = HolyDaysForGivenYear()

             for(i in 0 until array.length()){
               var obj =  array.get(i) as JSONObject
                holyDaysForGivenYear.year = obj.get("year") as Int
                holyDaysForGivenYear.holyDays = obj.get("holyDays").toString()
                holyDayDao.insert(holyDaysForGivenYear)

             }
        }
    }

}
package com.elna.db

import android.arch.persistence.room.TypeConverter
import com.elna.model.HolyDay
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.util.*

public class Converters : java.io.Serializable{

    var gson = Gson()

        @TypeConverter
        fun stringToSomeObjectList(data: String?): List<HolyDay> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<List<HolyDay>>() {

            }.type

            return gson.fromJson(data, listType)
        }

        @TypeConverter
        fun someObjectListToString(someObjects: List<HolyDay>): String {
            return gson.toJson(someObjects)
        }




}
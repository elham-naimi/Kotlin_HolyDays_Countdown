package com.elna.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.ColumnInfo
import com.elna.db.Converters


@Entity(tableName = "my_table")
class HolyDaysForGivenYear {
    @PrimaryKey
    var year: Int = 0
    @ColumnInfo(name = "ListData")
    public @TypeConverters(Converters::class)
    var holyDays: String =""


}


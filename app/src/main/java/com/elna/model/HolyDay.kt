package com.elna.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import org.threeten.bp.LocalDateTime


public data class  HolyDay( var holyDayName : String, var holyDayWhen : LocalDateTime)
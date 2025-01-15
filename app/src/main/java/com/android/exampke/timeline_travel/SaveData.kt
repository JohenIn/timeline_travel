package com.android.exampke.timeline_travel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SaveData(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "input") val input: String,
    @ColumnInfo(name = "result") val result: String,
)
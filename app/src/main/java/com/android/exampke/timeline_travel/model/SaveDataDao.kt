package com.android.exampke.timeline_travel

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SaveDataDao {
    @Query("SELECT * FROM savedata")
    fun getAll(): Flow<List<SaveData>>

    @Insert
    fun insertAll(vararg saveDatas: SaveData)

    @Delete
    fun delete(saveData: SaveData)

    @Query("DELETE FROM SaveData WHERE landmarkName = :landmarkName")
    fun deleteByName(landmarkName: String)
}
package com.android.exampke.timeline_travel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [SaveData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun saveDataDao(): SaveDataDao

    companion object MyDb {
        @Volatile
        private var instance: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 데이터베이스 구조 변경: 예를 들어, 새로운 컬럼 추가
                database.execSQL("ALTER TABLE save_data ADD COLUMN new_column TEXT DEFAULT NULL")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,"room.db"
                )
                    .build()
                    .also { instance = it }
            }
        }
    }
}
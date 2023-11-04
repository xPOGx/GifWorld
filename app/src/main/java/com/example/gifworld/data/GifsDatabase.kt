package com.example.gifworld.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [GifDB::class, GifBan::class],
    exportSchema = false
)
abstract class GifsDatabase : RoomDatabase() {
    abstract fun gifsDao(): GifsDao

    companion object {
        @Volatile
        private var Instance: GifsDatabase? = null

        fun getDatabase(context: Context): GifsDatabase =
            Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    GifsDatabase::class.java,
                    "app_database"
                )
                    .build()
                    .also { Instance = it }
            }
    }
}
package com.example.movie2you.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movie2you.modelDB.GenreDb
import com.example.movie2you.modelDB.MovieDb
import com.example.movie2you.modelDB.MovieGenreCrossRef

object Mobile2YouDataBase {
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE genre ADD COLUMN xpto TEXT")
        }
    }

    @Database(entities = [MovieDb::class, GenreDb::class, MovieGenreCrossRef::class], version = 2)
    abstract class Mobile2YouRoomDatabase : RoomDatabase() {
        abstract fun movieDao(): MovieDao
        abstract fun genreDao(): GenreDao
        abstract fun movieGenreDao(): MovieGenreCrossRefDao
    }

    fun getDatabase(context: Context) : Mobile2YouRoomDatabase {
        return Room.databaseBuilder(
            context,
            Mobile2YouRoomDatabase::class.java, "class1_db"
        ).addMigrations(
            MIGRATION_1_2
        ).build()
    }
}

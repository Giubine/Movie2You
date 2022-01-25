package com.example.movie2you.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movie2you.modelDb.GenreDb
import com.example.movie2you.modelDb.MovieDb
import com.example.movie2you.modelDb.MovieGenreCrossRef

object MovieYouDataBase {
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE genre ADD COLUMN xpto TEXT")
        }
    }

    @Database(entities = [MovieDb::class,
        GenreDb::class, MovieGenreCrossRef::class], version = 2)
    abstract class MovieYouRoomDatabase : RoomDatabase() {
        abstract fun movieDao(): MovieDao
        abstract fun genreDao(): GenreDao
        abstract fun movieGenreDao(): MovieGenreCrossRefDao
    }

    fun getDatabase(context: Context) : MovieYouRoomDatabase {
        return Room.databaseBuilder(
            context,
            MovieYouRoomDatabase::class.java, "MovieYou_db"
        ).addMigrations(
            MIGRATION_1_2
        ).build()
    }
}

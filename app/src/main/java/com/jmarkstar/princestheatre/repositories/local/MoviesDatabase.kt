package com.jmarkstar.princestheatre.repositories.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jmarkstar.princestheatre.repositories.local.daos.MovieDao
import com.jmarkstar.princestheatre.repositories.local.daos.ProviderDao
import com.jmarkstar.princestheatre.repositories.entities.Movie
import com.jmarkstar.princestheatre.repositories.entities.Provider

@Database(
    entities = [
        Provider::class,
        Movie::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val providerDao: ProviderDao
    abstract val movieDao: MovieDao
}

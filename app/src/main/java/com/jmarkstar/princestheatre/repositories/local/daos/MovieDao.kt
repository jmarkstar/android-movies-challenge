package com.jmarkstar.princestheatre.repositories.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jmarkstar.princestheatre.repositories.entities.Movie

@Dao
interface MovieDao {

    @Query("SELECT COUNT(id) FROM movies")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<Movie>

    @Query("DELETE FROM movies")
    suspend fun deleteAll()
}

package com.jmarkstar.princestheatre.repositories.local.daos

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT COUNT(id) FROM movies")
    suspend fun count(): Int
}

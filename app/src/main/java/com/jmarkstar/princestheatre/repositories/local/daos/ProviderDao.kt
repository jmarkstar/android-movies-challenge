package com.jmarkstar.princestheatre.repositories.local.daos

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProviderDao {

    @Query("SELECT COUNT(id) FROM providers")
    suspend fun count(): Int
}

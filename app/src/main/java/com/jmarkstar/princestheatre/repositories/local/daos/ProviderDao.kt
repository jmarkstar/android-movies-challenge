package com.jmarkstar.princestheatre.repositories.local.daos

import androidx.room.Dao
import androidx.room.Query
import com.jmarkstar.princestheatre.repositories.entities.Provider

@Dao
interface ProviderDao {

    @Query("SELECT COUNT(id) FROM providers")
    suspend fun count(): Int

    @Query("SELECT * FROM providers")
    suspend fun getAll(): List<Provider>
}

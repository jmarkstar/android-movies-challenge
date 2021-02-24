package com.jmarkstar.princestheatre.repositories.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "providers",
    indices = [Index("name")]
)

data class Provider(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
)

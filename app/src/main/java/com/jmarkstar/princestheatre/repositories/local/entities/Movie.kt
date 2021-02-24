package com.jmarkstar.princestheatre.repositories.local.entities

import androidx.room.Entity
import androidx.room.Index
import com.jmarkstar.princestheatre.common.Constants

@Entity(
    tableName = "movies",
    indices = [Index("title")],
    primaryKeys = ["id"]
)

data class Movie(
    val id: String,
    val type: String,
    val title: String,
    val poster: String,
    val year: String = Constants.EMPTY,
    val rated: String = Constants.EMPTY,
    val released: String = Constants.EMPTY,
    val runTime: String = Constants.EMPTY,
    val genre: String = Constants.EMPTY,
    val director: String = Constants.EMPTY,
    val writer: String = Constants.EMPTY,
    val actors: String = Constants.EMPTY,
    val plot: String = Constants.EMPTY,
    val language: String = Constants.EMPTY,
    val country: String = Constants.EMPTY,
    val production: String = Constants.EMPTY,
    val price: Double = 0.0
)

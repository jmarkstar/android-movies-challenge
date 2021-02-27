package com.jmarkstar.princestheatre.repositories.entities

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = Provider.TABLE_NAME,
    indices = [Index("id")],
    primaryKeys = ["id"]
)

data class Provider(
    val id: String,
    val name: String
) {
    companion object {
        const val TABLE_NAME = "providers"
    }

    fun toContentValues() = ContentValues().apply {
        this.put("id", id)
        this.put("name", name)
    }
}

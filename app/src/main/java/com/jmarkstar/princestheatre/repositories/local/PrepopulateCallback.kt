package com.jmarkstar.princestheatre.repositories.local

import android.database.sqlite.SQLiteDatabase
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jmarkstar.princestheatre.repositories.entities.Provider
import timber.log.Timber

class PrepopulateCallback : RoomDatabase.Callback() {

    companion object {

        val fakeProviders = arrayListOf(
            Provider("filmworld", "Film World"),
            Provider("cinemaworld", "Cinema World")
        )
    }

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        try {
            fakeProviders.forEach {
                db.insert(Provider.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE, it.toContentValues())
            }
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }
}

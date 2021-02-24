package com.jmarkstar.princestheatre.di

import android.content.Context
import androidx.room.Room
import com.jmarkstar.princestheatre.BuildConfig
import com.jmarkstar.princestheatre.common.security.Passphrases
import com.jmarkstar.princestheatre.repositories.local.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Module
@InstallIn(ApplicationComponent::class)
class LocalModule {

    @Provides
    fun provideSupportFactory(): SupportFactory? {
        val passphrase: ByteArray =
            SQLiteDatabase.getBytes(Passphrases.dbPassphrase.toCharArray())
        return if (BuildConfig.enableEncryption) SupportFactory(passphrase) else null
    }

    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context,
        supportFactory: SupportFactory? = null
    ) =
        Room.databaseBuilder(
            appContext,
            MoviesDatabase::class.java,
            "movies.db"
        )
            .openHelperFactory(supportFactory)
            .build()
}

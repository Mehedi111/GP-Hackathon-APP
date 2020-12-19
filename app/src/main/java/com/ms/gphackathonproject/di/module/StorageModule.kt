package com.ms.gphackathonproject.di.module

import android.content.Context
import androidx.room.Room
import com.gm.lollipop.data.storage.LocalStorage
import com.gm.lollipop.data.storage.PreferenceStorage
import com.gm.lollipop.storage.db.download.OfflineDaoAccess
import com.ms.gphackathonproject.Constants
import com.ms.gphackathonproject.data.storage.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */

@Module
@InstallIn(ApplicationComponent::class)
class StorageModule {

    @Singleton
    @Provides
    fun provideLocalStorage(context: Context): LocalStorage {
        return PreferenceStorage(context)
    }


    @Singleton
    @Provides
    fun provideLollipopDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideOfflineDaoAccess(appDatabase: AppDatabase): OfflineDaoAccess {
        return appDatabase.offlineDaoAccess()
    }

}
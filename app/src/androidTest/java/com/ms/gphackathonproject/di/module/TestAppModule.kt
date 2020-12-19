package com.ms.gphackathonproject.di.module

import android.content.Context
import androidx.room.Room
import com.ms.gphackathonproject.data.storage.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */
@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    // we want to create new instance on every new test cases, so we dont use singleton
    @Named("test_db")
    @Provides
    fun provideInMemoryDB(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}
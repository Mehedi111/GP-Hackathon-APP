package com.gm.lollipop.di.module

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Created by Mehedi Hasan on 11/25/2020.
 */
@Module
@InstallIn(ApplicationComponent::class)
abstract class ContextModule {

    @Binds
    abstract fun provideContext(@ApplicationContext context: Context): Context
}
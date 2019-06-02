package com.nytimes.sample.di

import android.content.Context
import androidx.work.WorkerFactory
import com.gobinda.facilities.App
import com.gobinda.facilities.worker2.SampleWorkerFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * App Module
 */
@Module
abstract class AppModule {

    /**
     * Expose Application as an injectable context
     */
    @Binds
    internal abstract fun bindContext(app: App): Context

    @Binds
    internal abstract fun bindWorkerFactory(app: SampleWorkerFactory): WorkerFactory




}
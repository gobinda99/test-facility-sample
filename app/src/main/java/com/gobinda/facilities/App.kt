package com.gobinda.facilities

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerFactory
import com.gobinda.facilities.di.DaggerAppComponent
//import com.gobinda.facilities.di.DaggerAppComponent
import com.gobinda.facilities.worker2.DaggerSampleComponent
import com.gobinda.facilities.worker2.SampleWorkerFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

/**
 * Application Class
 *
 */
class App : DaggerApplication()  {



    private val appComponent: AndroidInjector<App> by lazy {

        DaggerAppComponent
//            .builder()
            .factory()
            .create(this)
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent


    override fun onCreate() {
        super.onCreate()
        // Used Timber for logs
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val factory: SampleWorkerFactory = DaggerSampleComponent.create().factory()
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(factory).build())



//        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())


    }

}
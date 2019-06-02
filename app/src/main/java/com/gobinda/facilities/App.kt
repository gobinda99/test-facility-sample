package com.gobinda.facilities

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerFactory
//import com.gobinda.facilities.di.DaggerAppComponent
//import com.gobinda.facilities.di.DaggerAppComponent
import com.gobinda.facilities.worker2.SampleWorkerFactory
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject
import android.app.Activity
import com.gobinda.facilities.di.DaggerAppComponent
import dagger.android.HasActivityInjector


/**
 * Application Class
 *
 */
class App : Application (), HasActivityInjector {

//   @Inject
//   lateinit var workerFactory: SampleWorkerFactory

//    private val appComponent: AndroidInjector<App> by lazy {
//
//        DaggerAppComponent
////            .builder()
//            .factory()
//            .create(this)
//    }

    @Inject
    lateinit var injector : DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var factory: SampleWorkerFactory




    override fun onCreate() {
        super.onCreate()
        // Used Timber for logs
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }


        DaggerAppComponent.builder()
            .application(this)
            .build().inject(this)



//        val factory: SampleWorkerFactory = DaggerSampleComponent.create().factory()
//        appComponent.inject(this)
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(factory).build())



//        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())


    }

    override fun activityInjector(): AndroidInjector<Activity> = injector

}
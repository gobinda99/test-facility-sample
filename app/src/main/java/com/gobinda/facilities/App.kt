package com.gobinda.facilities

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import timber.log.Timber
import javax.inject.Inject
import android.app.Activity
import com.gobinda.facilities.di.AppComponent
import com.gobinda.facilities.di.DaggerAppComponent
import dagger.android.HasActivityInjector


/**
 * Application Class
 *
 */
class App : Application(), HasActivityInjector   {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    lateinit var appComponent : AppComponent


    override fun onCreate() {
        super.onCreate()
        // Used Timber for logs
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

       appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}
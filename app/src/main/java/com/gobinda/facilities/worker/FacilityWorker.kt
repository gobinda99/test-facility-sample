package com.gobinda.facilities.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.gobinda.facilities.App
import com.gobinda.facilities.data.store
import com.gobinda.facilities.di.HasInjector
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class FacilityWorker (
     private val appContext: Context,
     private val params: WorkerParameters
) : Worker(appContext, params), HasInjector<App> {

    override fun doWork(): Result {

        Timber.d("doWork")

        val dataSource = injector().appComponent.dataSource()

        dataSource.api.getFacilityData()
            .subscribeOn(Schedulers.trampoline())
            .subscribe({
                store(dataSource.database, it).subscribe({}, { Timber.e(it) })
            }, { Timber.e(it) })


        return Result.success()
    }

    override fun injector(): App = applicationContext as App

}



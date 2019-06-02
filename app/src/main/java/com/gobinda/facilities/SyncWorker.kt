package com.gobinda.facilities

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.gobinda.facilities.data.DataSource
import timber.log.Timber
import javax.inject.Inject

class SyncWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

//    @Inject
//    lateinit var dataSource : DataSource


    override fun doWork(): Result {
        Timber.d(" Sync Worker called")
        return Result.success()
    }
}
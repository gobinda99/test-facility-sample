package com.gobinda.facilities

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.gobinda.facilities.data.DataSource
import javax.inject.Inject

class SyncWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    @Inject
    lateinit var dataSource : DataSource


    override fun doWork(): Result {
        applicationContext is App
        // Do the work here--in this case, upload the images.

         //todo here
        // Indicate whether the task finished successfully with the Result
        return Result.success()
    }
}
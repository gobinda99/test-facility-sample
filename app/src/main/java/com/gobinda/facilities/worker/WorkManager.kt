package com.gobinda.facilities.worker

import androidx.work.*
import java.util.concurrent.TimeUnit

private fun createConstraints() = Constraints.Builder()
    .setRequiredNetworkType(NetworkType.CONNECTED)
    .setRequiresBatteryNotLow(true)
    .setRequiresStorageNotLow(true)
    .build()

private fun createWorkRequest(data: Data) =
    PeriodicWorkRequestBuilder<FacilityWorker>(WorkerManager.INTERVAL_HOURS, TimeUnit.HOURS)
    .setInputData(data)
    .setConstraints(createConstraints())
    .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
    .build()

fun startWorkManagerIfNotInitiated() {
    val work = createWorkRequest(Data.EMPTY)
    /* enqueue a work, ExistingPeriodicWorkPolicy.KEEP means that if this work already existits, it will be kept
    if the value is ExistingPeriodicWorkPolicy.REPLACE, then the work will be replaced */
    WorkManager.getInstance().enqueueUniquePeriodicWork(WorkerManager.WORKER_NAME,
        ExistingPeriodicWorkPolicy.KEEP, work)

}

private object WorkerManager {
    const val INTERVAL_HOURS = 24L
    const val WORKER_NAME = "FacilityWorker"
}





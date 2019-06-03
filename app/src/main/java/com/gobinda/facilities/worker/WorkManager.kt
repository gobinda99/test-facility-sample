package com.gobinda.facilities.worker

import androidx.work.*
import java.util.concurrent.TimeUnit

fun createConstraints() = Constraints.Builder()
    .setRequiredNetworkType(NetworkType.UNMETERED)
    .setRequiresBatteryNotLow(true)
    .setRequiresStorageNotLow(true)
    .build()

fun createWorkRequest(data: Data) = PeriodicWorkRequestBuilder<FacilityWorker>(24, TimeUnit.HOURS)  // setting period to 12 hours
    .setInputData(data)
    .setConstraints(createConstraints())
    .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
    .build()

fun startFacilityWork() {
    // set the input data, it is like a Bundle
    val work = createWorkRequest(Data.EMPTY)
    /* enqueue a work, ExistingPeriodicWorkPolicy.KEEP means that if this work already existits, it will be kept
    if the value is ExistingPeriodicWorkPolicy.REPLACE, then the work will be replaced */
    WorkManager.getInstance().enqueueUniquePeriodicWork("FacilityWorker", ExistingPeriodicWorkPolicy.KEEP, work)

    // Observe the result od the work
    /*WorkManager.getInstance().getWorkInfoByIdLiveData(work.id)
        .observe(lifecycleOwner, Observer { workInfo ->
            if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                // FINISHED SUCCESSFULLY!
            }
        })*/


}

//WorkManager workManager = WorkManager.getInstance();
//List<WorkStatus> value = workManager.getStatusesByTag(CALL_INFO_WORKER).getValue();
//if (value == null) {
//    WorkRequest callDataRequest = new PeriodicWorkRequest.Builder(CallInfoWorker.class,
//        24, TimeUnit.HOURS, 3, TimeUnit.HOURS)
//        .addTag(CALL_INFO_WORKER)
//        .build();
//    workManager.enqueue(callDataRequest);
//}



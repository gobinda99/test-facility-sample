package com.gobinda.facilities.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.gobinda.facilities.data.DataSource
import com.gobinda.facilities.data.store
import com.gobinda.facilities.di.worker.ChildWorkerFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class FacilityWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val dataSource: DataSource
) : Worker(appContext, params) {
    private val TAG = "FacilityWorker"
    override fun doWork(): Result {
        Log.d(TAG, "Hello world!")
        Log.d(TAG, "Injected foo: $dataSource")
        dataSource.api.getData().subscribeOn(Schedulers.trampoline()).subscribe({
             store(dataSource.database, it).subscribe({},{Timber.e(it)})
         }, { Timber.e(it)})

        return Result.success()
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}



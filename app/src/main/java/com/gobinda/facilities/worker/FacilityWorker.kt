package com.gobinda.facilities.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.gobinda.facilities.data.DataSource
import com.gobinda.facilities.di.worker.ChildWorkerFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

//class Foo @Inject constructor()

class FacilityWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val foo: DataSource
) : Worker(appContext, params) {
    private val TAG = "HelloWorldWorker"
    override fun doWork(): Result {
        Log.d(TAG, "Hello world!")
        Log.d(TAG, "Injected foo: $foo")
        return Result.success()
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}



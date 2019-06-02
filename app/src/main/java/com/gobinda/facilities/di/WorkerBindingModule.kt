package com.gobinda.facilities.di

import com.gobinda.facilities.di.worker.ChildWorkerFactory
import com.gobinda.facilities.di.worker.WorkerKey
import com.gobinda.facilities.worker.FacilityWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(FacilityWorker::class)
    fun bindFacilityWorker(factory: FacilityWorker.Factory): ChildWorkerFactory
}
package com.gobinda.facilities.worker

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module


@Module(includes = [AssistedInject_FacilityWorkerAssistedInjectModule::class])
@AssistedModule
interface FacilityWorkerAssistedInjectModule


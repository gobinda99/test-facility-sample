package com.gobinda.facilities.di

import com.gobinda.facilities.App
import com.gobinda.facilities.worker.FacilityWorkerAssistedInjectModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import dagger.BindsInstance




/**
 * Application component consisting of
 * App Module, Activity Binding module.
 * The Scope is Singleton it life will be a
 * active entire application
 *
 */
@Singleton
@Component(
    modules = arrayOf(
        AppModule::class,
        ActivityBindingModule::class,
        AndroidSupportInjectionModule::class,
        FacilityWorkerAssistedInjectModule::class,
        WorkerBindingModule::class
    )
)
interface AppComponent  {


     @Component.Builder
     interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent

    }

    fun inject(app: App)


}

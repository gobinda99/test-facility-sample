package com.gobinda.facilities.di

import com.gobinda.facilities.App
import com.gobinda.facilities.worker2.SampleAssistedInjectModule
import com.gobinda.facilities.worker2.SampleWorkerFactory
import com.gobinda.facilities.worker2.WorkerBindingModule
import com.nytimes.sample.di.AppModule
import dagger.Component
import dagger.android.AndroidInjector
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
        SampleAssistedInjectModule::class,
        WorkerBindingModule::class
    )
)
//interface AppComponent : AndroidInjector<App> {
//
//         fun workerFactory(): SampleWorkerFactory
//
//
//
////    @Component.Builder
////    abstract class Builder : AndroidInjector.Builder<App>()
//
//     @Component.Factory
//     abstract class Factory : AndroidInjector.Factory<App>
//
//
////     fun workerFactory(): SampleWorkerFactory
//
//}
interface AppComponent  {

//     fun workerFactory(): SampleWorkerFactory

     @Component.Builder
     interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent

    }

    fun inject(app: App)


}

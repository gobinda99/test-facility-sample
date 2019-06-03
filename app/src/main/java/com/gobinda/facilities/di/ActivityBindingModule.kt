package com.gobinda.facilities.di

import com.gobinda.facilities.ui.base.MainActivity
import com.gobinda.facilities.ui.FacilityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Activity Binding module, it uses @ContributesAndroidInjector.
 * It will be used to create subComponent Scope Instance and  the life
 * of the scope instance which like as long as the activity & Fragment.
 */

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FacilityModule::class])
    internal abstract fun mainActivity(): MainActivity



}
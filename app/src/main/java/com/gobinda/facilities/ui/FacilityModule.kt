package com.gobinda.facilities.ui

import com.gobinda.facilities.di.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FacilityModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun navFragment(): NavFacilityFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun optionFragment(): OptionsFragment
}
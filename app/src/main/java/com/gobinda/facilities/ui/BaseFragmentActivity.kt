//package com.gobinda.facilities.ui
//
//import android.app.Activity
//import android.app.Fragment
//import android.os.Bundle
//import androidx.fragment.app.FragmentActivity
//import dagger.android.*
//import java.util.*
//import javax.inject.Inject
//
//abstract class BaseFragmentActivity : (), HasFragmentInjector {
//
//    @Inject
//    lateinit var fragmentInjector : DispatchingAndroidInjector<Fragment>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        AndroidInjection.inject(this)
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun fragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
//
//}
package com.gobinda.facilities

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.gobinda.facilities.di.ViewModelProviderFactory
import com.gobinda.facilities.ui.FacilitiesViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : FragmentActivity() {

    @Inject
    lateinit var factory : ViewModelProviderFactory
     val model : FacilitiesViewModel by lazy {
         ViewModelProviders.of(this, factory).get(FacilitiesViewModel::class.java)
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        model.callApi()

    }
}

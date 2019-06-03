package com.gobinda.facilities.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.gobinda.facilities.di.ViewModelProviderFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var factory : ViewModelProviderFactory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
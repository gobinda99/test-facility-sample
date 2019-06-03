package com.gobinda.facilities.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gobinda.facilities.R
import com.gobinda.facilities.data.model.Facility
import com.gobinda.facilities.di.ActivityScope
import com.gobinda.facilities.ui.adapter.NavFacilityAdapter
import com.gobinda.facilities.ui.base.BaseFragment
import kotlinx.android.synthetic.main.frag_navigation.*
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject

@ActivityScope
class NavFacilityFragment @Inject constructor() : BaseFragment() {


    private val viewModel : FacilitiesViewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(FacilitiesViewModel::class.java)
    }

    val callback : NavItemCallback by lazy { context as NavItemCallback}

    val navAdapter = NavFacilityAdapter(
        ArrayList(0),
        { callback.onItemClick(it, true)
            Timber.d(it.name) })



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_navigation, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiAndListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.facilities.observe(this, Observer {
            navAdapter.addItem(it,true)
            if(!it.isNullOrEmpty()) {
                it[0].selected = true
                callback.onItemClick(it[0], false)

            }
        })
    }

    private fun setupUiAndListener() {
        with(nav_recycleView) {
            setHasFixedSize(true)
            adapter = navAdapter
            val manager = LinearLayoutManager(context)
            layoutManager = manager
            addItemDecoration(DividerItemDecoration(context,manager.orientation))

        }

    }

    interface NavItemCallback{
        fun onItemClick(facility : Facility, closeNav : Boolean)
    }

}
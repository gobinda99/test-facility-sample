package com.gobinda.facilities.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gobinda.facilities.R
import com.gobinda.facilities.di.ActivityScope
import com.gobinda.facilities.ui.adapter.OptionsAdapter
import com.gobinda.facilities.ui.base.BaseFragment
import kotlinx.android.synthetic.main.frag_navigation.*
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject

@ActivityScope
class OptionsFragment @Inject constructor() : BaseFragment() {

    val model : FacilitiesViewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(FacilitiesViewModel::class.java)
    }

    val navAdapter = OptionsAdapter(
        ArrayList(0),
        { Timber.d(it.name) })



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_option, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiAndListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model.requestOption(arguments!!.getString(FACILITY_ID)!!)
        model.optionForVisibleFacility.observe(this, Observer {
            navAdapter.addItem(it,true)
        })
    }


    private fun setupUiAndListener() {
        with(nav_recycleView) {
            setHasFixedSize(true)
            adapter = navAdapter
            val manager = LinearLayoutManager(context)
            layoutManager = manager
            /*addItemDecoration(DividerItemDecoration(context,manager.orientation))*/

        }


    }

    companion object {

        const val FACILITY_ID = "facility_id"

        fun newInstance(facilityId: String) = OptionsFragment().apply {
            arguments = Bundle().apply {
                putString(FACILITY_ID, facilityId)
            }
        }
    }

}
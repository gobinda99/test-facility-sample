package com.gobinda.facilities.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gobinda.facilities.R
import com.gobinda.facilities.di.ActivityScope
import com.gobinda.facilities.di.ViewModelProviderFactory
import com.gobinda.facilities.ui.adapter.OptionsAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.frag_navigation.*
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject

@ActivityScope
class OptionsFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var factory : ViewModelProviderFactory

    val model : FacilitiesViewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(FacilitiesViewModel::class.java)
    }

    val navAdapter = OptionsAdapter(
        ArrayList(0),
        { Timber.d(it.name) })

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_option, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiAndListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model.getOption(arguments!!.getString(FACILITY_ID)!!)
        model.optionMutableLiveData.observe(this, Observer {
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
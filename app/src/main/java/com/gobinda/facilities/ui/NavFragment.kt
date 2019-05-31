package com.gobinda.facilities.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.gobinda.facilities.R
import com.gobinda.facilities.data.api.Facility
import com.gobinda.facilities.ui.adapter.NavAdapter
import kotlinx.android.synthetic.main.frag_navigation.*
import timber.log.Timber
import java.util.ArrayList

class NavFragment : Fragment() {

    val navAdapter = NavAdapter(
        ArrayList(0),
        { Timber.d(it.name) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_navigation, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiAndListener()
    }

    override fun onResume() {
        super.onResume()
        val list = listOf<Facility>( Facility(name = "a"),
            Facility(name = "a"),Facility(name = "a"),
                Facility(name = "a"),Facility(name = "a"),
            Facility(name = "a"),Facility(name = "a"),Facility(name = "a"),
            Facility(name = "a"))
        navAdapter.addItem(list)
    }


    private fun setupUiAndListener() {
        with(nav_recycleView) {
            setHasFixedSize(true)
            adapter = navAdapter
            val manager = androidx.recyclerview.widget.LinearLayoutManager(context)
            layoutManager = manager
            /*addItemDecoration(DividerItemDecoration(context,manager.orientation))*/

        }


    }

}
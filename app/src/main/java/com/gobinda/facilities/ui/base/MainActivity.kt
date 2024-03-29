package com.gobinda.facilities.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gobinda.facilities.R
import com.gobinda.facilities.data.model.Facility
import com.gobinda.facilities.di.ViewModelProviderFactory
import com.gobinda.facilities.ui.FacilitiesViewModel
import com.gobinda.facilities.ui.NavFacilityFragment
import com.gobinda.facilities.ui.OptionsFragment
import com.gobinda.facilities.util.showSnackBar
import com.gobinda.facilities.worker.startWorkManagerIfNotInitiated
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : BaseAppCompatActivity(), NavFacilityFragment.NavItemCallback {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    val model: FacilitiesViewModel by lazy {
        ViewModelProviders.of(this, factory).get(FacilitiesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        callAndObserveVModel()

        startWorkManagerIfNotInitiated()

    }

    private fun callAndObserveVModel() {
        model.init()

        model.errorEvent.observe(this, Observer {
            if (it.contentIfNotHandled != null) {
                drawer_layout.showSnackBar(getString(R.string.msg_failed_to_refresh), 2000)
            }
        })

        model.loading.observe(this, Observer {
            if (it) {
                container.visibility = View.GONE
                empty_view.visibility = View.GONE
                loader.visibility = View.VISIBLE
            } else {
                loader.visibility = View.GONE
            }
        })

        model.error.observe(this, Observer {
            if (it) {
                container.visibility = View.GONE
                empty_view.visibility = View.VISIBLE
                loader.visibility = View.GONE
            } else {
                empty_view.visibility = View.GONE
            }
        })

        model.facilities.observe(this, Observer {
            container.visibility = View.VISIBLE
        })

        empty_view.setOnClickListener { model.retry() }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onItemClick(facility: Facility, closeNav: Boolean) {
        if (!isFinishing() && !isDestroyed()) {
            with(facility) {
                setTitle(name)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, OptionsFragment.newInstance(id)).commit()
            }

        }

        if (closeNav)
            drawer_layout.closeDrawer(GravityCompat.START)
    }
}

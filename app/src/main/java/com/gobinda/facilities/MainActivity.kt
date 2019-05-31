package com.gobinda.facilities

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.gobinda.facilities.di.ViewModelProviderFactory
import com.gobinda.facilities.ui.BaseFragmentActivity
import com.gobinda.facilities.ui.FacilitiesViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class MainActivity : BaseFragmentActivity() {

    @Inject
    lateinit var factory : ViewModelProviderFactory

    val model : FacilitiesViewModel by lazy {
         ViewModelProviders.of(this, factory).get(FacilitiesViewModel::class.java)
     }

    override fun onCreate(savedInstanceState: Bundle?) {
//        AndroidInjection.inject(this)
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


    }


}

package com.gobinda.facilities.ui

import androidx.lifecycle.ViewModel
import com.gobinda.facilities.data.DataSource
import io.reactivex.android.schedulers.AndroidSchedulers

class FacilitiesViewModel(val data: DataSource) : ViewModel() {

    fun callApi(){
        data.api.getData().observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

}
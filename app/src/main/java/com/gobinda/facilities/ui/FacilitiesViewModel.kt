package com.gobinda.facilities.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gobinda.facilities.data.DataSource
import com.gobinda.facilities.data.api.Facility
import com.gobinda.facilities.data.api.JsonResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import timber.log.Timber

class FacilitiesViewModel(val data: DataSource) : ViewModel() {

    init {
        Timber.d("times")
        callApi()
    }

    val mutableLiveData : MutableLiveData<List<Facility>> = MutableLiveData()

    private fun callApi(){
        data.api.getData().observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutableLiveData.value = it.facilities
            }, { e ->

                Timber.e(e)
            })
    }

}
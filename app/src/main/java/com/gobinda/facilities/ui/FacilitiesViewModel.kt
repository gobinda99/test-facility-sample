package com.gobinda.facilities.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gobinda.facilities.data.DataSource
import com.gobinda.facilities.data.api.Exclusions
import com.gobinda.facilities.data.api.Facility
import com.gobinda.facilities.data.api.JsonResponse
import com.gobinda.facilities.data.api.Option
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import timber.log.Timber

class FacilitiesViewModel(val data: DataSource) : ViewModel() {

    init {
        Timber.d("times")
        callApi()
    }

    val mutableLiveData : MutableLiveData<List<Facility>> = MutableLiveData()

    private var exclusions: List<List<Exclusions>?>? = null;

    val optionMutableLiveData : MutableLiveData<List<Option>> = MutableLiveData()

    private fun callApi(){
        data.api.getData().observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                exclusions = it.exclusions
                mutableLiveData.value = it.facilities
            }, { e ->

                Timber.e(e)
            })
    }

    fun getOption(facilityId : String ){
       optionMutableLiveData.value = mutableLiveData.value?.filter {
            it.facilityId == facilityId
        }?.firstOrNull()?.options
    }

//    fun optionSelected(facilityId: String, optionId: String) {
//        exclusions.filter { it?.filter { it.facilityId == facilityId && it.optionsId == optionId } }
//    }


}
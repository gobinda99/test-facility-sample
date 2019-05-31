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

        val sec: MutableList<Exclusions> = mutableListOf()

        mutableLiveData.value?.map { f ->
            f.options.map { o ->
                if (o.selected) {
                    sec.add(Exclusions(f.facilityId!!, o.id))
                    Timber.d(" ttt  " + f.facilityId+"    " + o.id +" "+o.name)
                }

            }
        }

        val options =  mutableLiveData.value?.filter {
            it.facilityId == facilityId
        }?.firstOrNull()?.options

        options!!.forEach {
            option ->
            option.disabled = false
            if(!option.selected)
            exclusions!!.forEach { list1 ->
                sec.forEach { sec ->
                    list1!!.forEach {
                        if(it.facilityId == sec.facilityId && it.optionsId == sec.optionsId){
                           list1.filter { !(it.facilityId == sec.facilityId) }.forEach {
                               if(it.facilityId == facilityId && it.optionsId == option.id )
                                   option.disabled = true;
                           }
                        }
                    }
                }

            }
        }
        optionMutableLiveData.value = options

    }




}
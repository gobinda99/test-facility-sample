package com.gobinda.facilities.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gobinda.facilities.data.DataSource
import com.gobinda.facilities.data.api.Exclusions
import com.gobinda.facilities.data.api.Facility
import com.gobinda.facilities.data.api.Option
import io.reactivex.android.schedulers.AndroidSchedulers
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
        val selectedOptions: MutableList<Exclusions> = mutableListOf()
        mutableLiveData.value?.map { fac ->
            fac.options.map { opt ->
                if (opt.selected) {
                    selectedOptions.add(Exclusions(fac.facilityId!!, opt.id))
                    Timber.d(" ttt  %s %s %s " , fac.facilityId, opt.id , opt.name)
                }
            }
        }
        val options = mutableLiveData.value?.filter {
            it.facilityId == facilityId
        }?.firstOrNull()?.options

        options?.forEach lit@{ opt ->
            opt.disabled = false
            if (!opt.selected)
                exclusions?.forEach { muExcl ->
                    selectedOptions.forEach { selOpt ->
                        muExcl!!.forEach {
                            if (it.facilityId == selOpt.facilityId && it.optionsId == selOpt.optionsId) {
                                muExcl.filter { !(it.facilityId == selOpt.facilityId) }.forEach {
                                    if (it.facilityId == facilityId && it.optionsId == opt.id) {
                                        opt.disabled = true;
                                        return@lit
                                    }
                                }
                            }
                        }
                    }
                }
        }
        optionMutableLiveData.value = options

    }




}
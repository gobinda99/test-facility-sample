package com.gobinda.facilities.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gobinda.facilities.data.DataSource
import com.gobinda.facilities.data.model.Exclusions
import com.gobinda.facilities.data.model.Facility
import com.gobinda.facilities.data.model.Option
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
            .subscribe({ res ->
                exclusions = res.exclusions
                mutableLiveData.value = res.facilities
                data.database.facilityDao()
                    .insertFacilities(res.facilities!!)
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        res.facilities.forEach { fac ->
                            fac.options?.map { it.facilityId = fac.id }
                            data.database.optionDao().insertOption(fac.options!!)
                                .subscribe({
                                    // test code of option
                                    /* data.database.optionDao()
                                         .loadOptions()
                                         .subscribe({
                                             Timber.d( " Store %s ",it.size)
                                         },{})*/
                                }, {
                                    Timber.e(it)
                                })
                            // test code of option
                            /*data.database.facilityDao().loadFacilityWithOptions()
                                .subscribe({
                                    val size = it.map { it.facility }
                                        .get(0)?.options?.size
                                    Timber.d("Store s %s",size)
                                },{Timber.e(it)})*/
                        }
                    }

            }, { e ->

                Timber.e(e)
            })
    }

    fun getOption(facilityId : String ){
        val selectedOptions: MutableList<Exclusions> = mutableListOf()
        mutableLiveData.value?.map { fac ->
            fac.options?.map { opt ->
                if (opt.selected) {
                    selectedOptions.add(Exclusions(fac.id, opt.id))
                    Timber.d(" ttt  %s %s %s " , fac.id, opt.id , opt.name)
                }
            }
        }
        val options = mutableLiveData.value?.filter {
            it.id == facilityId
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
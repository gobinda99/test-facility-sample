package com.gobinda.facilities.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gobinda.facilities.data.DataSource
import com.gobinda.facilities.data.load
import com.gobinda.facilities.data.model.Exclusions
import com.gobinda.facilities.data.model.Facility
import com.gobinda.facilities.data.model.Option
import com.gobinda.facilities.data.store
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FacilitiesViewModel(val data: DataSource) : ViewModel() {

    init {
        Timber.d("times")
        setData()
    }

    val mutableLiveData: MutableLiveData<List<Facility>> = MutableLiveData()

    private var exclusions: List<List<Exclusions>?>? = null;

    val optionMutableLiveData: MutableLiveData<List<Option>> = MutableLiveData()


    private fun setData() {
        data.database.facilityDao().loadFacility()
            .subscribeOn(Schedulers.io()).subscribe({
                if(it.size <= 0) {}
                  callData()
            },{
                  callApi()
            })
    }

    private fun callData() {
        load(data.database).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                if(!it.facilities.isNullOrEmpty()) {
                    mutableLiveData.value = it.facilities
                    exclusions = it.exclusions
                }

            },{Timber.e(it)})
    }

    fun callApi() {
        data.api.getData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()
            ).subscribe({
            store(data.database, it).subscribe({
                callData()
            },{Timber.e(it)})
        }, { Timber.e(it)})

    }

//    private fun callApi() {
//        data.api.getData().observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ res ->
//                exclusions = res.exclusions
//                mutableLiveData.value = res.facilities
//                data.database.facilityDao()
//                    .insertFacilities(res.facilities!!)
//                    .subscribeOn(Schedulers.io())
//                    .subscribe {
//                        res.facilities.forEach { fac ->
//                            fac.options?.map { it.facilityId = fac.id }
//                            data.database.optionDao().insertOption(fac.options!!)
//                                .subscribe({
//                                    // test code of option
//                                    /* data.database.optionDao()
//                                         .loadOptions()
//                                         .subscribe({
//                                             Timber.d( " Store %s ",it.size)
//                                         },{})*/
//                                }, {
//                                    Timber.e(it)
//                                })
//                            // test code of get option with option
//                            /*data.database.facilityDao().loadFacilityWithOptions()
//                                .subscribe({
//                                    val size = it.map { it.facility }
//                                        .get(0)?.options?.size
//                                    Timber.d("Store s %s",size)
//                                },{Timber.e(it)})*/
//                        }
//                        res.exclusions?.forEach { list ->
//                            list?.let {
//
//
//                            }
//                        }
//                       /* res.exclusions?.let {
//                            for (i in 1..it.size) {
//                                val listEx = it.get(i)
//                                listEx?.let {
//                                    it.forEach {
//                                        it.id = i.toString()
//                                    }
//                                    data.database.exclusionsDao().insertExclusions(it)
//                                        .subscribe({
//                                            Timber.d("Success")
//                                            //Insert success
//                                        }, {
//                                            Timber.e(it)
//                                        })
//                                }
//                            }
//
//                        }*/
//
//                        res.exclusions?.forEachIndexed { index, exclusList ->
//                            exclusList?.forEach { it.id = index.toString() }
//                            exclusList?.let {
//                                data.database.exclusionsDao().insertExclusions(it)
//                                    .subscribe({
//                                        Timber.d("Success")
//                                        //Insert success
//                                    }, {
//                                        Timber.e(it)
//                                    })
//                            }
//
//                        }
//
//                        data.database.exclusionsDao().loadExclusions()
//                            .subscribe({
//                               val exclusions1 : List<List<Exclusions>> =  it.groupBy { it.id }.values.toList()
//                            }, {
//                                Timber.e(it)
//                            })
//                    }
//
//            }, { e ->
//
//                Timber.e(e)
//            })
//    }

    fun getOption(facilityId: String) {
        val selectedOptions: MutableList<Exclusions> = mutableListOf()
        mutableLiveData.value?.map { fac ->
            fac.options?.map { opt ->
                if (opt.selected) {
                    selectedOptions.add(Exclusions(fac.id, opt.id))
                    Timber.d(" ttt  %s %s %s ", fac.id, opt.id, opt.name)
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
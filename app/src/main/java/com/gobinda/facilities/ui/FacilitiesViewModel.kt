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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FacilitiesViewModel(val data: DataSource) : ViewModel() {

    private val disposable = CompositeDisposable()

    val facilities: MutableLiveData<List<Facility>> = MutableLiveData()

    private var exclusions: List<List<Exclusions>?>? = null;

    val optionForVisibleFacility: MutableLiveData<List<Option>> = MutableLiveData()

    fun start() {
        disposable.add(data.database.facilityDao().loadFacility()
            .subscribeOn(Schedulers.io()).subscribe({
                if(!it.isEmpty()) {
                    loadFromDB()
                } else {
                    api()
                }
            },{
                  api()
                Timber.e(it)
            }))
    }

    private fun loadFromDB() {
        disposable.add(load(data.database).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                if (!it.facilities.isNullOrEmpty()) {
                    facilities.value = it.facilities
                    exclusions = it.exclusions
                }

            }, { Timber.e(it) })
        )
    }

    private fun api() {
        disposable.add(data.api.getData().subscribeOn(Schedulers.io())
            .subscribe({
                store(data.database, it).subscribe({
                    loadFromDB()
                }, { Timber.e(it) })
            }, { Timber.e(it) })
        )

    }

//    private fun callApi() {
//        data.api.getData().observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ res ->
//                exclusions = res.exclusions
//                facilities.value = res.facilities
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

    fun requestOption(facilityId: String) {
        val selectedOptions: MutableList<Exclusions> = mutableListOf()
        facilities.value?.map { fac ->
            fac.options?.map { opt ->
                if (opt.selected) {
                    selectedOptions.add(Exclusions(fac.id, opt.id))
                    Timber.d(" Option Selected  %s %s %s ", fac.id, opt.id, opt.name)
                }
            }
        }
        val options = facilities.value?.filter {
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
        optionForVisibleFacility.value = options

    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }


}
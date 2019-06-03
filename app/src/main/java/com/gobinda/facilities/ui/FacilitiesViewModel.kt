package com.gobinda.facilities.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gobinda.facilities.data.DataSource
import com.gobinda.facilities.data.load
import com.gobinda.facilities.data.model.Exclusions
import com.gobinda.facilities.data.model.Facility
import com.gobinda.facilities.data.model.Option
import com.gobinda.facilities.data.store
import com.gobinda.facilities.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FacilitiesViewModel(val data: DataSource) : ViewModel() {

    private val disposable = CompositeDisposable()


    private var exclusions: List<List<Exclusions>?>? = null;

    val error: MutableLiveData<Event<Throwable>> = MutableLiveData()

    val facilities: MutableLiveData<List<Facility>> = MutableLiveData()

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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                store(data.database, it)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                    loadFromDB()
                }, { Timber.e(it) })
            }, {
                error.value = Event(it)
                Timber.e(it)
            })
        )

    }


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
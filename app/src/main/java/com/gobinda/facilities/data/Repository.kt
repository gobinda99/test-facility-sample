package com.gobinda.facilities.data

import com.gobinda.facilities.data.api.FacilityData
import com.gobinda.facilities.data.model.Exclusions
import com.gobinda.facilities.data.model.Facility
import com.gobinda.facilities.data.room.FacilitiesDatabase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


fun store(database: FacilitiesDatabase, data: FacilityData): Completable {
    return Completable.fromCallable {
        database.facilityDao().deleteAllFacility().subscribe({
            database.facilityDao()
                .insertFacilities(data.facilities!!)
                .subscribeOn(Schedulers.trampoline())
                .subscribe {
                    data.facilities.forEach { facility ->
                        facility.options?.map { it.facilityId = facility.id }
                        database.optionDao().insertOption(facility.options!!)
                            .subscribe({
                            }, {
                                Timber.e(it)
                            })
                    }
                    data.exclusions?.forEachIndexed { index, exclusList ->
                        exclusList?.forEach { it.id = index.toString() }
                        exclusList?.let {
                            database.exclusionsDao().insertExclusions(it)
                                .subscribe({
                                }, {
                                    Timber.e(it)
                                })
                        }

                    }

                }
        }, { Timber.e(it) })
    }
}


fun load(database: FacilitiesDatabase): Single<FacilityData> {
    return Single.create { emitter ->
        var facilities: List<Facility>?
        var exclusions: List<List<Exclusions>>? = null
        database.facilityDao().loadFacilityWithOptions()
            .subscribeOn(Schedulers.trampoline())
            .subscribe({
                facilities = it.map { it.facility }
                if (it.isNotEmpty()) {
                    database.exclusionsDao().loadExclusions()
                        .subscribe({
                            exclusions = it.groupBy { it.id }.values.toList()
                            emitter.onSuccess(FacilityData(facilities, exclusions))
                        }, {
                            Timber.e(it)
                        })
                }
            }, { Timber.e(it) })

    }


}



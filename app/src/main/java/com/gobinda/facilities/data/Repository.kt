package com.gobinda.facilities.data

import com.gobinda.facilities.data.api.FacilityData
import com.gobinda.facilities.data.model.Exclusions
import com.gobinda.facilities.data.model.Facility
import com.gobinda.mvp.sample.room.FacilitiesDatabase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.Callable


fun store(database: FacilitiesDatabase, data: FacilityData) : Completable{
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
                                // test code of option
                                /* data.database.optionDao()
                                 .loadOptions()
                                 .subscribe({
                                     Timber.d( " Store %s ",it.size)
                                 },{})*/
                            }, {
                                Timber.e(it)
                            })
                        // test code of get option with option
                        /*data.database.facilityDao().loadFacilityWithOptions()
                        .subscribe({
                            val size = it.map { it.facility }
                                .get(0)?.options?.size
                            Timber.d("Store s %s",size)
                        },{Timber.e(it)})*/
                    }
//                data?.forEach { list ->
//                    list?.let {
//
//
//                    }
//                }
                    /* res.exclusions?.let {
                     for (i in 1..it.size) {
                         val listEx = it.get(i)
                         listEx?.let {
                             it.forEach {
                                 it.id = i.toString()
                             }
                             data.database.exclusionsDao().insertExclusions(it)
                                 .subscribe({
                                     Timber.d("Success")
                                     //Insert success
                                 }, {
                                     Timber.e(it)
                                 })
                         }
                     }

                 }*/

                    data.exclusions?.forEachIndexed { index, exclusList ->
                        exclusList?.forEach { it.id = index.toString() }
                        exclusList?.let {
                            database.exclusionsDao().insertExclusions(it)
                                .subscribe({
                                    Timber.d("Success")
                                    //Insert success
                                }, {
                                    Timber.e(it)
                                })
                        }

                    }

//                database.exclusionsDao().loadExclusions()
//                    .subscribe({
//                        val exclusions1 : List<List<Exclusions>> =  it.groupBy { it.id }.values.toList()
//                    }, {
//                        Timber.e(it)
//                    })
                }
        }, { Timber.e(it) })
    }
}

//fun load(database: FacilitiesDatabase): FacilityData {
////    return Single.fromCallable(Callable<List<Facility>>() {
////    })
//    var facilities : List<Facility>?= null
//    var exclusions: List<List<Exclusions>>? = null
//    database.facilityDao().loadFacilityWithOptions()
//        .subscribe({
//            facilities = it.map { it.facility }
//            if(it.isNotEmpty()) {
//                database.exclusionsDao().loadExclusions()
//                    .subscribe({
//                         exclusions = it.groupBy { it.id }.values.toList()
//                    }, {
//                        Timber.e(it)
//                    })
//            }
//        }, { Timber.e(it) })
////    if(facilities != null ) {
//       return FacilityData(facilities,exclusions)
////    }
////    return null
//
//}

fun load(database: FacilitiesDatabase): Single<FacilityData> {
    return Single.create { emitter ->
        var facilities: List<Facility>? = null
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

//    if(facilities != null ) {
//    return FacilityData(facilities,exclusions)
//    }
//    return null

}



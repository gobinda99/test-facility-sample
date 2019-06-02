package com.gobinda.mvp.sample.room

import androidx.room.*
import com.gobinda.facilities.data.model.Facility
import com.gobinda.facilities.data.model.FacilityImpl
import com.gobinda.facilities.data.model.Option
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Data access class
 * Insert, update, select, delete operation/query using RxJava so thread
 * scheduling, non block ui operation can be maintained easily.
 */
@Dao
interface  FacilityDao {

    @Query("SELECT * FROM facility")
    fun loadFacility(): Single<List<Facility>>

    @Transaction
    @Query("SELECT * FROM facility")
    fun loadFacilityWithOptions(): Single<List<FacilityImpl>>

   /* @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOption( options : List<Option>) : Completable*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertFacilities(vararg users : Facility) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertFacilities(users : List<Facility>) : Completable

    @Query("DELETE FROM facility")
    fun deleteAllFacility(): Completable


//
//    @Query("SELECT * from flight_events where id = :id LIMIT 1")
//    fun loadFlightEventById(id: Int): Single<FlightEvent>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertFlightEvents(flights: List<FlightEvent>): Completable
//
//    @Query("DELETE FROM flight_events")
//    fun deleteAllFlightEvents(): Completable
}

//fun FacilityDao.insertFacilitiesWithOptions(users : List<Facility>) : Completable {
//    return Completable.create {
//        try {
//            insertFacilities(users).subscribe()
//            users.forEach { fac ->
//                fac.options.map { it.facilityId = fac.id  }
//                insertOption(fac.options).subscribe()
//                it.onComplete()
//            }
//
//        }catch (e : Throwable) {
//            it.onError(e)
//        }
//    }
//}
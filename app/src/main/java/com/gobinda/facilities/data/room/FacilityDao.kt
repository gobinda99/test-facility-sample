package com.gobinda.mvp.sample.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gobinda.facilities.data.model.Facility
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Data access class
 * Insert, update, select, delete operation/query using RxJava so thread
 * scheduling, non block ui operation can be maintained easily.
 */
@Dao
interface FacilityDao {

    @Query("SELECT * FROM facility")
    fun loadFacility(): Single<List<Facility>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFacilities(vararg users : Facility) : Completable

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
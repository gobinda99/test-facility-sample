package com.gobinda.mvp.sample.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gobinda.facilities.data.room.Data
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Data access class Flight Event Model.
 * Insert, update, select, delete operation/query using RxJava so thread
 * scheduling, non block ui operation can be maintained easily.
 */
@Dao
interface FacilitiesDao {

    @Query("SELECT * FROM data")
    fun loadFlightEvents(): Single<List<Data>>
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
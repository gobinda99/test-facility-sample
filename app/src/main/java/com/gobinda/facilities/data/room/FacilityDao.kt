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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFacilities(vararg users : Facility) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertFacilities(users : List<Facility>) : Completable

    @Query("DELETE FROM facility")
    fun deleteAllFacility(): Completable

}

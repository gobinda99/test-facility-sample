package com.gobinda.mvp.sample.room

import androidx.room.*
import com.gobinda.facilities.data.model.Exclusions
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
interface  ExclusionsDao {

    @Query("SELECT * FROM exclusions")
    fun loadExclusions(): Single<List<Exclusions>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertExclusions(exclusions : List<Exclusions>) : Completable

    @Query("DELETE FROM exclusions")
    fun deleteAllExclusions(): Completable
}

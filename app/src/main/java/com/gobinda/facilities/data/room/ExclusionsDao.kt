package com.gobinda.facilities.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gobinda.facilities.data.model.Exclusions
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Data access class
 * Insert, update, select, delete operation/query using RxJava so thread
 * scheduling, non block ui operation can be maintained easily.
 */
@Dao
interface ExclusionsDao {

    @Query("SELECT * FROM exclusions")
    fun loadExclusions(): Single<List<Exclusions>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExclusions(exclusions: List<Exclusions>): Completable

    /*@Query("DELETE FROM exclusions")
    fun deleteAllExclusions(): Completable*/
}

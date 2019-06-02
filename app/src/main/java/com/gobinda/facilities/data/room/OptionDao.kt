package com.gobinda.facilities.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gobinda.facilities.data.model.Facility
import com.gobinda.facilities.data.model.Option
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface OptionDao {

    @Query("SELECT * FROM option")
    fun loadOptions(): Single<List<Option>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOption(vararg options : Option) : Completable

    @Query("SELECT * from option where facility_id = :facilityID LIMIT 1")
    fun loadOptionByFacilityId(facilityID: Int): Single<List<Option>>

}
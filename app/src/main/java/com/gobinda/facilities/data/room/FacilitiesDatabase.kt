package com.gobinda.facilities.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gobinda.facilities.data.model.Exclusions
import com.gobinda.facilities.data.model.Facility
import com.gobinda.facilities.data.model.Option
import com.gobinda.facilities.data.room.OptionDao

/**
 * ORM Database Helper class to connect to the database
 * named facility-sample.db and its records
 */
@Database(entities = [Facility::class, Option::class, Exclusions:: class], version = 1)
abstract class FacilitiesDatabase : RoomDatabase() {

    abstract fun facilityDao(): FacilityDao
    abstract fun optionDao(): OptionDao
    abstract fun exclusionsDao(): ExclusionsDao

    companion object {

        @Volatile
        private var INSTANCE: FacilitiesDatabase? = null

        fun getInstance(context: Context): FacilitiesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: bindDatabase(context).also { INSTANCE = it }
            }

        private fun bindDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, FacilitiesDatabase::class.java, "facility-sample.db")
                .build()
    }
}

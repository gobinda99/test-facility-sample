package com.gobinda.mvp.sample.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gobinda.facilities.data.model.Facility
import com.gobinda.facilities.data.model.Option
import com.gobinda.facilities.data.room.OptionDao

/**
 * ORM Database Helper class to connect to the database
 * named flight-sample.db and its records
 */
@Database(entities = [Facility::class, Option::class], version = 1)
abstract class FacilitiesDatabase : RoomDatabase() {

    abstract fun facilityDao(): FacilityDao
    abstract fun optionDao(): OptionDao

    companion object {

        @Volatile
        private var INSTANCE: FacilitiesDatabase? = null

        fun getInstance(context: Context): FacilitiesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: bindDatabase(context).also { INSTANCE = it }
            }

        private fun bindDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, FacilitiesDatabase::class.java, "flight-sample.db")
                .build()
    }
}

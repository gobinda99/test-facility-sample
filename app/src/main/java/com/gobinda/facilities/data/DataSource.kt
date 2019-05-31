package com.gobinda.facilities.data

import android.content.Context
import com.gobinda.facilities.data.api.RestApi
import com.gobinda.mvp.sample.room.FacilitiesDatabase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * DataSource class to access database, and api.
 * This object is created  through dagger (DI) and it is used
 * in dependency injection through Object Graph.
 * The scope is Singleton it instance life will be entire application
 */
@Singleton
class DataSource @Inject constructor(private val context: Context) {
//    val database = FacilitiesDatabase.getInstance(context)
    val api = RestApi.api
}
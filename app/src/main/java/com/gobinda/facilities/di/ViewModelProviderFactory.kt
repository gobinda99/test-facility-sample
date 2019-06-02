package com.gobinda.facilities.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gobinda.facilities.data.DataSource
import com.gobinda.facilities.ui.FacilitiesViewModel
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class ViewModelProviderFactory @Inject constructor(private val dataSource: DataSource)
    : ViewModelProvider.NewInstanceFactory() {

    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(FacilitiesViewModel::class.java)) {
             FacilitiesViewModel(dataSource) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
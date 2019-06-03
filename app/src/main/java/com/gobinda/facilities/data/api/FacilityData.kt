package com.gobinda.facilities.data.api

import com.gobinda.facilities.data.model.Exclusions
import com.gobinda.facilities.data.model.Facility

data class FacilityData(
    val facilities: List<Facility>?,
    val exclusions: List<List<Exclusions>?>?
)









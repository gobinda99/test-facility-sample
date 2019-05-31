package com.gobinda.facilities.data.api

data class JsonResponse(
    val facilities: List<Facility>?,
    val exclusions: List<List<Exclusions>?>?
)

data class Facility(
    val facilityId: String? = null,
    val name: String,
    val options: List<Option>? = null
)

data class Option(
    val id: String,
    val name: String,
    val icon: String
)

data class Exclusions(
    val facilityId: String,
    val optionsId: String
)


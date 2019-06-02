package com.gobinda.facilities.data.model

import androidx.room.*
import org.jetbrains.annotations.NotNull

@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = Facility::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("facility_id"),
    onDelete = ForeignKey.CASCADE)
))
data class Option(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var icon: String = "",
    @Transient
    @ColumnInfo(name = "facility_id", index = true)
    var facilityId: String = "",
    @Transient
    var selected : Boolean = false,
    @Transient
    var disabled : Boolean = false
)
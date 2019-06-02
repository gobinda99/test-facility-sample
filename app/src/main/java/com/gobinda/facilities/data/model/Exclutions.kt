package com.gobinda.facilities.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    primaryKeys = ["facilityId", "optionsId", "id"],
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Facility::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("facilityId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Option::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("optionsId"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class Exclusions(
    @ColumnInfo(index = true)
    var facilityId: String = "",
    @ColumnInfo(index = true)
    var optionsId: String = "",
    var id: String = ""
)
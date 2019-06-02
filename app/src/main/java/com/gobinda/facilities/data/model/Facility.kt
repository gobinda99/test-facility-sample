package com.gobinda.facilities.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.gobinda.facilities.data.model.Option
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity
data class Facility(
    @PrimaryKey
    @SerializedName("facility_id")
    var id: String = "",
    var name: String = "",
    @Ignore
    @Relation(parentColumn = "id", entityColumn = "facility_id")
    var options: List<Option> = emptyList(),
    @Transient
    var selected : Boolean = false
)
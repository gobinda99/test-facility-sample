package com.gobinda.facilities.data.model

import androidx.room.*
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
    var options: List<Option>? = emptyList(),
    @Transient
    var selected : Boolean = false
)

class FacilityImpl(
     facility: Facility,
     @Relation(parentColumn = "id", entityColumn = "facility_id", entity = Option::class)
     var options: List<Option>? = emptyList()
)  {
    @Embedded
    var facility  = facility
    get() {
        field.options = options
        return field
    }

}

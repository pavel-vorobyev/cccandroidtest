package com.pavelvorobyev.cccandroidtest.businesslogic.db.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "estimates")
data class Estimate(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: String,

    @ColumnInfo(name = "company")
    @SerializedName("company")
    val company: String,

    @ColumnInfo(name = "address")
    @SerializedName("address")
    val address: String,

    @ColumnInfo(name = "number")
    @SerializedName("number")
    val number: Int,

    @ColumnInfo(name = "revision_number")
    @SerializedName("revision_number")
    val revisionNumber: Int,

    @ColumnInfo(name = "created_date")
    @SerializedName("created_date")
    val createdDate: String,

    @ColumnInfo(name = "created_by")
    @SerializedName("created_by")
    val createdById: String,

    @ColumnInfo(name = "requested_by")
    @SerializedName("requested_by")
    val requestedById: String,

    @ColumnInfo(name = "contact")
    @SerializedName("contact")
    val contactId: String,

    )

data class EstimateAndPerson(

    @Embedded
    val estimate: Estimate,

    @Relation(
        parentColumn = "created_by",
        entityColumn = "id"
    )
    val createdBy: Person,

    @Relation(
        parentColumn = "requested_by",
        entityColumn = "id"
    )
    val requestedBy: Person,

    @Relation(
        parentColumn = "contact",
        entityColumn = "id"
    )
    val contact: Person

)
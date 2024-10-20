package com.ztb.pinkoo.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CategoryModel {


    @SerializedName("page"        ) var page       : Int?            = null
    @SerializedName("per_page"    ) var perPage    : Int?            = null
    @SerializedName("total"       ) var total      : Int?            = null
    @SerializedName("total_pages" ) var totalPages : Int?            = null
    @SerializedName("data"        ) var data       : ArrayList<Data> = arrayListOf()
    @SerializedName("support"     ) var support    : Support?        = Support()

    @Entity(tableName = "data_table")
    data class Data(
        @PrimaryKey(autoGenerate = true)
        @SerializedName("id") var id: Int? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("year") var year: Int? = null,
        @SerializedName("color") var color: String? = null,
        @SerializedName("pantone_value") var pantoneValue: String? = null

    ) : Serializable

    data class Support (

        @SerializedName("url"  ) var url  : String? = null,
        @SerializedName("text" ) var text : String? = null

    ): Serializable
}
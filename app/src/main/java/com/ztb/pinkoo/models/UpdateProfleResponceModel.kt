package com.ztb.pinkoo.models

import com.google.gson.annotations.SerializedName

data class UpdateProfleResponceModel(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("errorCode") var errorCode  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : Data?   = Data()
)
data class Data (

    @SerializedName("profile_url" ) var profileUrl : String? = null

)
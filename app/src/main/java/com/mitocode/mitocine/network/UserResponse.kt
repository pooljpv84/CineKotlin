package com.mitocode.mitocine.network

import com.google.gson.annotations.SerializedName

data class UserResponse
    (
    var data: UserDataResponse,
    var status: Boolean,
    var message:String
    )

data class UserDataResponse(
    var id : Int,
    var name : String,
    @SerializedName("lastName")
    var lName : String,
    var user : String,
    var password : String,
    var email : String,
    var address : String,
    var phone : String

)

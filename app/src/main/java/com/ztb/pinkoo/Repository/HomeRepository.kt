package com.ztb.pinkoo.Repository


import android.net.Uri
import com.ztb.pinkoo.api.ApiInterface
import okhttp3.MultipartBody
import okhttp3.RequestBody


class HomeRepository(private val apiInterface: ApiInterface) {


//    fun getuserlogin(email:String,password:String) = apiInterface.getuserLogin(email,password)
    fun getcatdetail() = apiInterface.getCategorydetail()
    fun getUserPaginationList(url:String) = apiInterface.getUserPaginationList(url)
    fun postLogin(map:HashMap<String,Any>) = apiInterface.postLogin(map)
    fun getProfilePicUpdate(uri: MultipartBody.Part, userId: RequestBody) = apiInterface.getProfilePicUpdate(uri,userId)

}
package com.ztb.pinkoo.Repository


import com.ztb.pinkoo.api.ApiInterface


class HomeRepository(private val apiInterface: ApiInterface) {


//    fun getuserlogin(email:String,password:String) = apiInterface.getuserLogin(email,password)
    fun getcatdetail() = apiInterface.getCategorydetail()
    fun getUserPaginationList(url:String) = apiInterface.getUserPaginationList(url)
    fun postLogin(map:HashMap<String,Any>) = apiInterface.postLogin(map)

}
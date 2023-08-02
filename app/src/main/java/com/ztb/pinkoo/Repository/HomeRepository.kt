package com.ztb.pinkoo.Repository


import com.ztb.pinkoo.api.ApiInterface


class HomeRepository(private val apiInterface: ApiInterface) {


//    fun getuserlogin(email:String,password:String) = apiInterface.getuserLogin(email,password)
    fun getcatdetail() = apiInterface.getCategorydetail()

}
package com.ztb.pinkoo.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.ztb.pinkoo.Repository.HomeRepository
import com.ztb.pinkoo.models.CategoryModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val tokenexpire = MutableLiveData<String>()
//    val getuserlogin = MutableLiveData<LoginModel>()
    val getcatdetail = MutableLiveData<CategoryModel>()


    fun getCategoryDetail(){
        homeRepository.getcatdetail().enqueue(object : Callback<CategoryModel?> {
            override fun onResponse(
                call: Call<CategoryModel?>,
                response: Response<CategoryModel?>
            ) {
                when {
                    response.isSuccessful -> {
                        getcatdetail.postValue(response.body())
                    }
                    response.code() == 400 -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val response = jObjError.getJSONObject("response").getString("message")
                        errorMessage.postValue(response)
                    }
                    response.code() == 401 -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val response = jObjError.getJSONObject("response").getString("message")
                        tokenexpire.postValue(response)
                    }
                    response.code() == 555 -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val response = jObjError.getJSONObject("response").getString("message")
                        errorMessage.postValue(response)
                    }
                    response.code() == 601 -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val response = jObjError.getJSONObject("response").getString("message")
                        errorMessage.postValue(response)
                    }
                    else -> {

                        errorMessage.postValue("Something Went Wrong")
                    }
                }
            }

            override fun onFailure(call: Call<CategoryModel?>, t: Throwable) {
                errorMessage.postValue("Something Went Wrong")
            }
        })
    }


//    fun getUserLogin(email:String,password:String){
//
//        homeRepository.getuserlogin(email,password).enqueue(object : Callback<LoginModel?> {
//            override fun onResponse(call: Call<LoginModel?>, response: Response<LoginModel?>) {
//                when {
//                    response.isSuccessful -> {
//                        getuserlogin.postValue(response.body())
//                    }
//                    response.code() == 400 -> {
//                        val jObjError = JSONObject(response.errorBody()!!.string())
//                        val response = jObjError.getJSONObject("response").getString("message")
//                        errorMessage.postValue(response)
//                    }
//                    response.code() == 401 -> {
//                        val jObjError = JSONObject(response.errorBody()!!.string())
//                        val response = jObjError.getJSONObject("response").getString("message")
//                        tokenexpire.postValue(response)
//                    }
//                    response.code() == 555 -> {
//                        val jObjError = JSONObject(response.errorBody()!!.string())
//                        val response = jObjError.getJSONObject("response").getString("message")
//                        errorMessage.postValue(response)
//                    }
//                    response.code() == 601 -> {
//                        val jObjError = JSONObject(response.errorBody()!!.string())
//                        val response = jObjError.getJSONObject("response").getString("message")
//                        errorMessage.postValue(response)
//                    }
//                    else -> {
//
//                        errorMessage.postValue("Something Went Wrong")
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<LoginModel?>, t: Throwable) {
//                errorMessage.postValue("Something Went Wrong")
//            }
//        })
//    }







}
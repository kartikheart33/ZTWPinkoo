package com.ztb.pinkoo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ztb.pinkoo.Factory.HomeFactory
import com.ztb.pinkoo.R
import com.ztb.pinkoo.Repository.HomeRepository
import com.ztb.pinkoo.ViewModel.HomeViewModel
import com.ztb.pinkoo.api.ApiInterface
import com.ztb.pinkoo.databinding.ActivityLoginBinding
import com.ztb.pinkoo.utils.AppConstants
import com.ztb.pinkoo.utils.AppUtil

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    lateinit var viewModel: HomeViewModel
    lateinit var apiInterface: ApiInterface
    private var appUtil = AppUtil()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiInterface = ApiInterface.create("")
        viewModel = ViewModelProvider(
            this,
            HomeFactory(HomeRepository(apiInterface))
        ).get(HomeViewModel::class.java)


//        binding.email.setText("eve.holt@reqres.in")
//        binding.password.setText("cityslicka")

        var isPasswodShow = true

        binding.showbtn.setOnClickListener {

            if (isPasswodShow) {
                isPasswodShow = false
                binding.showbtn.setImageResource(R.drawable.password_show)
                binding.password.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()

            } else {
                binding.showbtn.setImageResource(R.drawable.password_visible)
                binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
                isPasswodShow = true
            }
        }


        binding.submit.setOnClickListener {

            if (binding.email.text.toString().trim().isEmpty()) {
                appUtil.AlertDialogValidation(this, "Please provide your email.")

                return@setOnClickListener
            }
            if (binding.password.text.trim().isEmpty()) {
                appUtil.AlertDialogValidation(this, "Please provide your password.")

            } else if (appUtil.isInternetAvailable(this)) {
                val map:HashMap<String,Any> = HashMap<String,Any>()
                map.put("email",binding.email.text.toString())
                map.put("password",binding.password.text.toString())
            viewModel.postLogin(map)
                binding.progressBar.visibility=View.VISIBLE

            } else {
                appUtil.internetDialog(this, "Check Your Internet Connection")
            }


        }


        setObserver()
    }
    fun setObserver(){
        viewModel.token.observe(this){
            if(it!=null){
                Toast.makeText(this, ""+it.toString(), Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility=View.GONE
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        viewModel.error.observe(this){
            if(it!=null){
                binding.progressBar.visibility=View.GONE
                Toast.makeText(this, ""+it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
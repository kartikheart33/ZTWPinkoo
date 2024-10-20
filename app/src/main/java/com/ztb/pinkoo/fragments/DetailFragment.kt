package com.ztb.pinkoo.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.github.dhaval2404.imagepicker.ImagePicker
import com.ztb.pinkoo.Factory.HomeFactory
import com.ztb.pinkoo.R
import com.ztb.pinkoo.Repository.HomeRepository
import com.ztb.pinkoo.ViewModel.HomeViewModel
import com.ztb.pinkoo.api.ApiInterface
import com.ztb.pinkoo.databinding.FragmentDetailBinding
import com.ztb.pinkoo.models.CategoryModel
import com.ztb.pinkoo.utils.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class DetailFragment : BaseFragment() {
    lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    var selectedImageUri :Uri? = null
    var requestUri = ""
    lateinit var apiInterface: ApiInterface
    lateinit var viewModel: HomeViewModel
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil
    var list: ArrayList<CategoryModel.Data>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("catmodel") { _, bundle ->

            list = bundle.serializable("catmodel")
            Log.d(TAG, "onCreate: $list")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        sharedPreferenceUtil = SharedPreferenceUtil(requireContext())
        apiInterface = ApiInterface.createProfile()
        viewModel = ViewModelProvider(this, HomeFactory(HomeRepository(apiInterface))).get(HomeViewModel::class.java)
        sharedPreferenceUtil.setStringPreferences(AppConstants.ARGS_SAVE,args.secretAccessCode)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivProfile.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(
                    1080,
                    1080
                ).start()
        }
        setObserver()
    }

    private fun setObserver() {
        viewModel.getprofileUpdate.observe(viewLifecycleOwner){
           if (it?.status==1){
               binding.ivProfile.setImageURI(selectedImageUri)
               Toast.makeText(requireContext(), "${it.data?.profileUrl}", Toast.LENGTH_SHORT).show()
               Toast.makeText(requireContext(), "${sharedPreferenceUtil.getStringPreferences(AppConstants.ARGS_SAVE).toString()}", Toast.LENGTH_SHORT).show()
           }
               Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            data?.apply {
                selectedImageUri = this.data!!
                requestUri = this.data.toString().replace("file:///","")
                callApi()
            }
        }
    }

    private fun callApi() {
        var userimage: MultipartBody.Part? = null
        val file: File = File(requestUri)
        val requestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        userimage = MultipartBody.Part.createFormData("profile_photo", file.name, requestBody)
       val userid = RequestBody.create("text/plain".toMediaTypeOrNull(), "6710bd0eee7e6ea14e46ac32")
        viewModel.getProfileUpdate(userimage,userid)
    }


    companion object {
        private val TAG = DetailFragment::class.java.name
    }
}
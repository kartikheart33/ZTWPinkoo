package com.ztb.pinkoo.fragments

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ztb.pinkoo.Factory.HomeFactory
import com.ztb.pinkoo.MyApp
import com.ztb.pinkoo.R
import com.ztb.pinkoo.Repository.HomeRepository
import com.ztb.pinkoo.RoomDb.DataViewModel
import com.ztb.pinkoo.RoomDb.DataViewModelFactory
import com.ztb.pinkoo.ViewModel.HomeViewModel
import com.ztb.pinkoo.adapter.NewTypeAdapter
import com.ztb.pinkoo.api.ApiInterface
import com.ztb.pinkoo.databinding.FragmentHomeBinding
import com.ztb.pinkoo.models.CategoryModel


class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var adapter:NewTypeAdapter
    lateinit var apiInterface: ApiInterface
    var list: ArrayList<CategoryModel.Data> = ArrayList()
    lateinit var model: CategoryModel
    private val PERMISSION_REQUEST_CODE = 100
    private val dataViewModel: DataViewModel by viewModels {
        DataViewModelFactory((requireActivity().application as MyApp).repository)
    }
    // List of permissions required across different API levels
    private val permissions = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
        )
        else -> arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentHomeBinding.inflate(inflater,container,false)
        apiInterface = ApiInterface.create()
        homeViewModel = ViewModelProvider(this,HomeFactory(HomeRepository(apiInterface))).get(HomeViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callapi()
        setObserver()
        setOnClick()
        setUpAdapter()


    }

    private fun setUpAdapter() {
        adapter = NewTypeAdapter(list, listener = {
            Log.d(TAG, "setUpAdapter: ${it}")
            val newData = CategoryModel.Data(
                name = it.name,
                year = it.year,
                color = it.color,
                pantoneValue = it.pantoneValue
            )
            dataViewModel.insert(newData)

            // Example: Fetch all Data
            dataViewModel.getAllData { dataList ->
                dataList.forEach { println(it) }
            }
        })
        binding.rvFev.adapter = adapter
    }

    private fun checkPermissions(permissionsList: Array<String>): Boolean {
        permissionsList.forEach {
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(), it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }
    private fun setOnClick() {

        binding.btnNext.setOnClickListener {
            val directions = HomeFragmentDirections.actionNavigationHomeToNavigationDetail(secretAccessCode = "asdasd"
            )
            findNavController().navigate(directions)
           /* if (!checkPermissions(permissionsList = REQUIRED_PERMISSIONS)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    requestPermissionLauncher.launch(
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
                }else{
                    requestPermissionLauncher.launch(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                }

            } else {
                val directions = HomeFragmentDirections.actionNavigationHomeToNavigationDetail(secretAccessCode = "asdasd"
                )
                findNavController().navigate(directions)
            }*/


        }
    }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val directions = HomeFragmentDirections.actionNavigationHomeToNavigationDetail(secretAccessCode = "asdasd"
            )
            findNavController().navigate(directions)
            // Permission is granted. Continue the action or workflow in your
            // app.
        } else {
            // Explain to the user that the feature is unavailable because the
            // feature requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
        }
    }

    private fun setObserver() {
        homeViewModel.getcatdetail.observe(viewLifecycleOwner ){
            model = it
            list.clear()
            list.addAll(it.data)
            adapter.notifyDataSetChanged()
            it?.data?.let { data ->
                setFragmentResult(
                    "catmodel",
                    bundleOf(
                        "catmodel" to data as ArrayList<CategoryModel.Data>
                    )
                )


            }
        }
    }

    private fun callapi() {
        homeViewModel.getCategoryDetail()
    }

    companion object {
        private val TAG = HomeFragment::class.java.name
        private val REQUIRED_PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            mutableListOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
        ).toTypedArray()
        }else{
            mutableListOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).toTypedArray()

        }
    }
}
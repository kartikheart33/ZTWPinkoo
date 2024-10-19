package com.ztb.pinkoo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ztb.pinkoo.Factory.HomeFactory
import com.ztb.pinkoo.R
import com.ztb.pinkoo.Repository.HomeRepository
import com.ztb.pinkoo.ViewModel.HomeViewModel
import com.ztb.pinkoo.api.ApiInterface
import com.ztb.pinkoo.databinding.FragmentHomeBinding
import com.ztb.pinkoo.models.CategoryModel


class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var apiInterface: ApiInterface
    var list: ArrayList<CategoryModel.Data> = ArrayList()
    lateinit var model: CategoryModel
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

    }

    private fun setOnClick() {
        binding.btnNext.setOnClickListener {
            val directions = HomeFragmentDirections.actionNavigationHomeToNavigationDetail(secretAccessCode = "asdasd"
            )
            findNavController().navigate(directions)
        }

    }

    private fun setObserver() {
        homeViewModel.getCategoryDetail()
    }

    private fun callapi() {
        homeViewModel.getcatdetail.observe(viewLifecycleOwner ){
            model = it
          it?.data?.let { data ->
              list = data
              list.map {

              }
              setFragmentResult(
                  "catmodel",
                  bundleOf(
                      "catmodel" to data as ArrayList<CategoryModel.Data>
                  )
              )


           }
        }
    }

    companion object {
        private val TAG = HomeFragment::class.java.name
    }
}
package com.ztb.pinkoo.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.navArgs
import com.ztb.pinkoo.R
import com.ztb.pinkoo.utils.serializable
import com.ztb.pinkoo.databinding.FragmentDetailBinding
import com.ztb.pinkoo.models.CategoryModel
import com.ztb.pinkoo.utils.serializable
import com.ztb.pinkoo.utils.showToast

class DetailFragment : BaseFragment() {
    lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
     var model: CategoryModel.Data? = null
    var list: ArrayList<CategoryModel.Data>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("catmodel"){_,bundle ->

            list = bundle.serializable("catmodel")
            Log.d(TAG, "onCreate: $list")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        private val TAG = DetailFragment::class.java.name
    }
}
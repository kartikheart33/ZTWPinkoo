package com.ztb.pinkoo.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.ztb.pinkoo.activity.BaseActivity
import com.ztb.pinkoo.utils.hideKeyboard

open class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    companion object {
        private val TAG = BaseFragment::class.java.name
    }

    override fun onDestroyView() {
        view?.hideKeyboard()
        super.onDestroyView()
    }
}

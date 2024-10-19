package com.ztb.pinkoo.dialogs

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater

import com.ztb.pinkoo.R
import com.ztb.pinkoo.databinding.DialogProgressBinding


class ProgressDialog (context: Context) : AlertDialog(context, R.style.ProgressDialog) {

    private lateinit var binding: DialogProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogProgressBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        )
        setContentView(binding.root)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }
}

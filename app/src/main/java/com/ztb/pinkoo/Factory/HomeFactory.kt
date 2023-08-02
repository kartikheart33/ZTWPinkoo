package com.ztb.pinkoo.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ztb.pinkoo.Repository.HomeRepository
import com.ztb.pinkoo.ViewModel.HomeViewModel


class HomeFactory(val homeRepository: HomeRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(this.homeRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")

        }
    }
}
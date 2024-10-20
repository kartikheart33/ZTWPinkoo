package com.ztb.pinkoo.RoomDb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ztb.pinkoo.models.CategoryModel
import kotlinx.coroutines.launch

class DataViewModel(private val repository: DataRepository) : ViewModel() {

    fun insert(data: CategoryModel.Data) {
        viewModelScope.launch {
            repository.insert(data)
        }
    }

    fun update(data: CategoryModel.Data) {
        viewModelScope.launch {
            repository.update(data)
        }
    }

    fun getDataById(id: Int, callback: (CategoryModel.Data?) -> Unit) {
        viewModelScope.launch {
            val data = repository.getDataById(id)
            callback(data)
        }
    }

    fun getAllData(callback: (List<CategoryModel.Data>) -> Unit) {
        viewModelScope.launch {
            val dataList = repository.getAllData()
            callback(dataList)
        }
    }

    fun deleteById(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}
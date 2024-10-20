package com.ztb.pinkoo.RoomDb

import com.ztb.pinkoo.models.CategoryModel

class DataRepository(private val dataDao: DataDao) {

    suspend fun insert(data: CategoryModel.Data) = dataDao.insert(data)

    suspend fun update(data: CategoryModel.Data) = dataDao.update(data)

    suspend fun getDataById(id: Int): CategoryModel.Data? = dataDao.getDataById(id)

    suspend fun getAllData(): List<CategoryModel.Data> = dataDao.getAllData()

    suspend fun deleteById(id: Int) = dataDao.deleteById(id)

    suspend fun deleteAll() = dataDao.deleteAll()
}
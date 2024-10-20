package com.ztb.pinkoo.RoomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ztb.pinkoo.models.CategoryModel
@Dao
interface DataDao {
    @Insert
    suspend fun insert(data: CategoryModel.Data)

    @Update
    suspend fun update(data: CategoryModel.Data)

    @Query("SELECT * FROM data_table WHERE id = :id")
    suspend fun getDataById(id: Int): CategoryModel.Data?

    @Query("SELECT * FROM data_table")
    suspend fun getAllData(): List<CategoryModel.Data>

    @Query("DELETE FROM data_table WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM data_table")
    suspend fun deleteAll()
}
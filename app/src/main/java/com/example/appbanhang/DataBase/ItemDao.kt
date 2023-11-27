package com.example.appbanhang.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(entity: List<ItemEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun insert(entity: ItemEntity): Long?

    @Query("Select * From ItemDB where type=:string")
    fun getByName(string: String): Flow<ItemEntity>

    @Query("Select * From ItemDB")
    fun getAll(): List<ItemEntity>

    @Delete
    fun deleteSelectedItems(list: List<ItemEntity>)
}

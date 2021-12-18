package com.mosericko.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mosericko.todoapp.data.models.ToDoData

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table ORDER BY id DESC")
    fun getAllData(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)
    @Update
    suspend fun updateData(toDoData: ToDoData)
    @Delete
    suspend fun deleteItem(toDoData: ToDoData)
}
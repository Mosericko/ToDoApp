package com.mosericko.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mosericko.todoapp.data.models.ToDoData

@Database(entities = [ToDoData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    companion object {
        @Volatile
/*writes to this field are immediately made visible yo other threads*/
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {
            val temporaryInstance = INSTANCE
            if (temporaryInstance != null) {
                return temporaryInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "todo_database"
                ).build()

                INSTANCE = instance
                return instance
            }


        }
    }
}

/*When a thread calls synchronized, it acquires the lock of that synchronized block. Other threads
* don't have permission to call the same synchronized block as long as previous thread which had acquired
* the lock does not release the lock*/
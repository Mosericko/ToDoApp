package com.mosericko.todoapp.data

import androidx.room.TypeConverter
import com.mosericko.todoapp.data.models.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}

/*Room provides functionality for converting between primitive and boxed types but doesn't
* allow for object references between entities*/

/*For Our Case, when writing to the database, convert Priority.High -> String from our enum class
 and String ->Priority.High when reading from the Database
 */
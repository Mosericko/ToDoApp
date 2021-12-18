package com.mosericko.todoapp.data.viewmodel

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.mosericko.todoapp.R
import com.mosericko.todoapp.data.models.Priority

class SharedViewModel(application: Application) : AndroidViewModel(application) {


    val adapterListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        (parent?.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                application,
                                R.color.red
                            )
                        )
                    }
                    1 -> {
                        (parent?.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                application,
                                R.color.yellow
                            )
                        )
                    }
                    2 -> {
                        (parent?.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                application,
                                R.color.green1
                            )
                        )
                    }
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


    fun verifyInput(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High" -> {
                Priority.HIGH
            }
            "Medium" -> {
                Priority.MEDIUM
            }
            "Low" -> {
                Priority.LOW
            }
            else -> Priority.LOW
        }
    }

    fun parsePriorityLevel(priority: Priority): Int {

        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }

}
package com.mosericko.todoapp.fragments.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mosericko.todoapp.R
import com.mosericko.todoapp.data.models.ToDoData
import com.mosericko.todoapp.data.viewmodel.SharedViewModel
import com.mosericko.todoapp.data.viewmodel.ToDoViewModel
import com.mosericko.todoapp.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private lateinit var bindView: FragmentAddBinding
    private lateinit var priority: Spinner
    private lateinit var title: EditText
    private lateinit var desc: EditText
    private val toDoViewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindView = FragmentAddBinding.inflate(inflater, container, false)
        val context: Context? = this.context
        /*  //set menu
          setHasOptionsMenu(true)*/

        //connect views
        priority = bindView.spinner
        title = bindView.title
        desc = bindView.description

        //fetch priorities from string resources
        val priorities = resources.getStringArray(R.array.Priorities)

        //adapter
        val adapter = context?.let { ArrayAdapter(it, R.layout.drop_down_design, priorities) }

        //set Adapter
        priority.adapter = adapter
        priority.onItemSelectedListener = sharedViewModel.adapterListener

        //save task
        bindView.save.setOnClickListener {
            insertDataToDB()
        }

        return bindView.root


    }


    private fun insertDataToDB() {
        val titleStr = title.text.toString().trim()
        val priorityStr = priority.selectedItem.toString()
        val description = desc.text.toString().trim()

        val validation = sharedViewModel.verifyInput(titleStr, description)

        if (validation) {
            //insert Data to Database
            //create new ToDoData object

            val userData = ToDoData(
                0,
                titleStr,
                sharedViewModel.parsePriority(priorityStr),
                description
            )
            //insert the data to db
            toDoViewModel.insertData(userData)
            Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_SHORT).show()
            //navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)


        } else {
            Toast.makeText(requireContext(), "Missing title or description", Toast.LENGTH_SHORT)
                .show()
        }
    }


}



























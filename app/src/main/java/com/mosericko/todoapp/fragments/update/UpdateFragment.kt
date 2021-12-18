package com.mosericko.todoapp.fragments.update

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mosericko.todoapp.R
import com.mosericko.todoapp.data.models.ToDoData
import com.mosericko.todoapp.data.viewmodel.SharedViewModel
import com.mosericko.todoapp.data.viewmodel.ToDoViewModel
import com.mosericko.todoapp.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val toDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        binding.title.setText(args.currentItem.title)
        binding.description.setText(args.currentItem.description)
        binding.spinner.setSelection(sharedViewModel.parsePriorityLevel(args.currentItem.priority))
        binding.spinner.onItemSelectedListener = sharedViewModel.adapterListener
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> updateItem()
            R.id.delete -> confirmAction()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmAction() {
        val alert = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null)
        alert.setView(view)

        val yesBtn = view.findViewById<TextView>(R.id.yes)
        val noBtn = view.findViewById<TextView>(R.id.no)

        val alertD = alert.create()
        alertD.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertD.show()
        yesBtn.setOnClickListener {
            deleteItem()
            alertD.dismiss()
        }
        noBtn.setOnClickListener {
            alertD.dismiss()
        }
    }

    private fun deleteItem() {
        toDoViewModel.deleteItem(args.currentItem)
        Toast.makeText(context, "Task Deleted Successfully!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)

    }

    private fun updateItem() {
        val titleStr = binding.title.text.toString()
        val descriptionStr = binding.description.text.toString()
        val selectedPriority = binding.spinner.selectedItem.toString()

        val validation = sharedViewModel.verifyInput(titleStr, descriptionStr)
        if (validation) {
            val updatedItem = ToDoData(
                args.currentItem.id,
                titleStr,
                sharedViewModel.parsePriority(selectedPriority),
                descriptionStr
            )

            toDoViewModel.updateData(updatedItem)
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(context, "Missing Title or Description", Toast.LENGTH_SHORT).show()
        }
    }


}
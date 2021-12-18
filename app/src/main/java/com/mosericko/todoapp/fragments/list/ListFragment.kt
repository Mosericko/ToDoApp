package com.mosericko.todoapp.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mosericko.todoapp.R
import com.mosericko.todoapp.data.viewmodel.ToDoViewModel
import com.mosericko.todoapp.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var tasksRV: RecyclerView
    private val listAdapter: ListAdapter by lazy { ListAdapter() }
    private val toDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }


        tasksRV = binding.noteRecycler
        tasksRV.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        tasksRV.adapter = listAdapter
        toDoViewModel.getAllData.observe(
            viewLifecycleOwner,
            { data ->
                listAdapter.setData(data)
            })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }


}
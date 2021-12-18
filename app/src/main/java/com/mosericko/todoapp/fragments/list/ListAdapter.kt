package com.mosericko.todoapp.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mosericko.todoapp.R
import com.mosericko.todoapp.data.models.Priority
import com.mosericko.todoapp.data.models.ToDoData
import com.mosericko.todoapp.databinding.TaskCardBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListVH>() {
    var dataList = emptyList<ToDoData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_card, parent, false)
        return ListVH(TaskCardBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ListVH, position: Int) {
        val currentItem = dataList[position]
        holder.bindView.title.text = currentItem.title
        holder.bindView.desc.text = currentItem.description

        when (currentItem.priority) {
            Priority.HIGH -> holder.bindView.priorityColor.setCardBackgroundColor(
                getColor(holder.itemView.context, R.color.red)
            )
            Priority.MEDIUM -> holder.bindView.priorityColor.setCardBackgroundColor(
                getColor(
                    holder.itemView.context,
                    R.color.yellow
                )
            )
            Priority.LOW -> holder.bindView.priorityColor.setCardBackgroundColor(
                getColor(
                    holder.itemView.context,
                    R.color.green1
                )
            )
        }

        //onClickListener
        holder.bindView.root.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(toDoData: List<ToDoData>) {
        this.dataList = toDoData
        notifyDataSetChanged()
    }


    class ListVH(val bindView: TaskCardBinding) : RecyclerView.ViewHolder(bindView.root) {

    }
}
package com.example.sleeptracker.sleeptracker


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sleeptracker.database.SleepNightEntity
import com.example.sleeptracker.databinding.ListItemSleepTrackerBinding

class SleepNightAdapter : ListAdapter<SleepNightEntity, SleepNightAdapter.ViewHolder>(SleepNightDiffUtil()){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SleepNightAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SleepNightAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ListItemSleepTrackerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : SleepNightEntity){
            binding.sleep = item
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepTrackerBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)

            }
        }
    }

}

class SleepNightDiffUtil : DiffUtil.ItemCallback<SleepNightEntity>(){
    override fun areItemsTheSame(oldItem: SleepNightEntity, newItem: SleepNightEntity): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNightEntity, newItem: SleepNightEntity): Boolean {
        return oldItem == newItem
    }

}
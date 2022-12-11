package com.example.alarmclock

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.databinding.ItemAlarmlistBinding

class AlarmListAdapter(
    private val context: Context,
    val onClickDeleteInterface: AlarmClickDeleteInterface
): RecyclerView.Adapter<AlarmListAdapter.ViewHolder>() {

    private val list = ArrayList<Alarm>()

    inner class ViewHolder(val binding: ItemAlarmlistBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemAlarmlistBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]

        holder.binding.alarmTitle.text = currentItem.time

        holder.binding.btnDeleteAlarm.setOnClickListener {
            onClickDeleteInterface.onDeleteIconClick(currentItem)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: List<Alarm>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    interface AlarmClickDeleteInterface {
        fun onDeleteIconClick(alarm: Alarm)
    }


}
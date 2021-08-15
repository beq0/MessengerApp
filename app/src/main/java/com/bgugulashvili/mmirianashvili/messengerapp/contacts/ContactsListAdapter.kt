package com.bgugulashvili.mmirianashvili.messengerapp.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bgugulashvili.mmirianashvili.messengerapp.R
import java.util.*

class ContactsListAdapter(var list: ArrayList<ContactsListItem>) :
    RecyclerView.Adapter<ContactListItemViewHolder>() {

    var clickListener: ContactListItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contacts_list_item, parent, false)
        return ContactListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactListItemViewHolder, position: Int) {
        val item = list[position]
        holder.username.text = item.name
        holder.lastMessage.text = item.lastMessage
        holder.lastMessageTime.text = getLastMessageTimeStr(item.lastMessageTime)
        holder.itemView.setOnClickListener {
            clickListener?.onContactClicked(item)
        }
    }

    private fun getLastMessageTimeStr(lastMessageTime: Date): String {
        val diff: Long = Date().time - lastMessageTime.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        return when {
            hours == 0L -> {
                "$minutes min"
            }
            hours <= 23 -> {
                "$hours hr"
            }
            else -> {
                val cal = Calendar.getInstance()
                cal.time = lastMessageTime
                val day = cal.get(Calendar.DAY_OF_MONTH)
                val month = getMonthStr(cal.get(Calendar.MONTH))
                "$day $month"
            }
        }
    }

    private fun getMonthStr(month: Int): String {
        return when (month) {
            0 -> "JAN"
            1 -> "FEB"
            2 -> "MAR"
            3 -> "APR"
            4 -> "MAY"
            5 -> "JUN"
            6 -> "JUL"
            7 -> "AUG"
            8 -> "SEP"
            9 -> "OCT"
            10 -> "NOV"
            11 -> "DEC"
            else -> "N/M"
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(items: ArrayList<ContactsListItem>) {
        list.clear()
        list.addAll(items)
        this.notifyDataSetChanged()
    }

    fun insertData(item: ContactsListItem) {
        list.add(item)
        notifyItemChanged(list.size - 1)
    }

    fun removeData() {
        val size = list.size
        list.clear()
        notifyItemRangeRemoved(0, size)
    }
}

class ContactListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var username = itemView.findViewById<TextView>(R.id.contact_item_name)
    var lastMessage = itemView.findViewById<TextView>(R.id.contact_item_last_message)
    var lastMessageTime = itemView.findViewById<TextView>(R.id.contact_item_last_message_time)
}

interface ContactListItemClickListener {
    fun onContactClicked(item: ContactsListItem)
}
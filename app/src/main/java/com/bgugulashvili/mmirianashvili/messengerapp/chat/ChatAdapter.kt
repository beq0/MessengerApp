package com.bgugulashvili.mmirianashvili.messengerapp.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.bgugulashvili.mmirianashvili.messengerapp.R
import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import java.util.*

class ChatAdapter(var list: ArrayList<ChatItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatItemViewHolder {
        return if (viewType == MY_CHAT_ITEM) {
            ChatItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.my_chat_text_item, parent, false)
            )
        } else {
            ChatItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.their_chat_text_item, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].uid == AuthUtils.getCurrentUserUid()) {
            MY_CHAT_ITEM
        } else {
            THEIR_CHAT_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holderView = holder as ChatItemViewHolder
        val item = list[position]
        holderView.message.text = item.message
        holderView.messageTime.text = getMessageTimeStr(item.date)
        if (position != list.size - 1) {
            if (getItemViewType(position + 1) == getItemViewType(position)) {
                val param = holderView.message.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(holderView.message.marginLeft,
                    20, holderView.message.marginRight,holderView.message.marginBottom)
                holderView.message.layoutParams = param
            }
        }
    }

    private fun getMessageTimeStr(messageTime: Date): String {
        val diff: Long = Date().time - messageTime.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val cal = Calendar.getInstance()
        cal.time = Date()
        val currYear = cal.get(Calendar.YEAR)
        cal.time = messageTime
        val minute =
            if (cal.get(Calendar.MINUTE).toString().length == 1) "0${cal.get(Calendar.MINUTE)}"
            else cal.get(Calendar.MINUTE)
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val month = getMonthStr(cal.get(Calendar.MONTH))
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val year = cal.get(Calendar.YEAR)
        return when {
            hours < 24 -> {
                "$hour:$minute"
            }
            currYear == year -> {
                "$day $month $hour:$minute"
            }
            else -> {
                "$year $month $day $hour:$minute"
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

    fun updateData(items: ArrayList<ChatItem>) {
        list.clear()
        list.addAll(items)
        this.notifyDataSetChanged()
    }

    fun insertData(item: ChatItem) {
        list.add(item)
        notifyItemChanged(list.size - 1)
    }

    companion object {
        private const val THEIR_CHAT_ITEM = 1
        private const val MY_CHAT_ITEM = 2
    }
}

class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var message = itemView.findViewById<TextView>(R.id.chat_text)
    var messageTime = itemView.findViewById<TextView>(R.id.chat_text_time)
}
package com.bgugulashvili.mmirianashvili.messengerapp.usersearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bgugulashvili.mmirianashvili.messengerapp.R

class UserSearchUserAdapter(var list: ArrayList<UserSearchUserItem>) :
    RecyclerView.Adapter<UserSearchUserItemViewHolder>() {

    var clickListener: UserSearchUserClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserSearchUserItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_search_user_item, parent, false)
        return UserSearchUserItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserSearchUserItemViewHolder, position: Int) {
        val item = list[position]
        holder.username.text = item.name
        holder.profession.text = item.profession
        holder.itemView.setOnClickListener {
            clickListener?.onUserClicked(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(items: ArrayList<UserSearchUserItem>) {
        list.clear()
        list.addAll(items)
        this.notifyDataSetChanged()
    }
}

class UserSearchUserItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var username = itemView.findViewById<TextView>(R.id.user_search_user_name)
    var profession = itemView.findViewById<TextView>(R.id.user_search_user_profession)
}

interface UserSearchUserClickListener {
    fun onUserClicked(item: UserSearchUserItem)
}
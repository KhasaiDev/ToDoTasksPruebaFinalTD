package com.example.todotaskspruebafinaltd.apadter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todotaskspruebafinaltd.R
import com.example.todotaskspruebafinaltd.model.User

class UserAdapter(
    private val onDeleteClickListener: (User) -> Unit,
    private val onCheckBoxClickListener: (User, Boolean) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var userList = emptyList<User>()

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val username: TextView = itemView.findViewById(R.id.tvUsername)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.username.text = currentUser.username

        holder.deleteButton.setOnClickListener {
            onDeleteClickListener(currentUser)
        }

        holder.checkBox.setOnCheckedChangeListener(null) // Prevents unwanted callback triggers
        holder.checkBox.isChecked = currentUser.isChecked

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            currentUser.isChecked = isChecked
            onCheckBoxClickListener(currentUser, isChecked)
        }

        if (currentUser.isChecked) {
            holder.username.paintFlags = holder.username.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.username.paintFlags = holder.username.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setUsers(users: List<User>){
        userList = users
        notifyDataSetChanged()
    }
}

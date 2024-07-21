package com.example.ToDoTask

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ToDoTask.adapter.UserAdapter
import com.example.ToDoTask.databinding.MainActivityBinding
import com.example.ToDoTask.model.User
import com.example.ToDoTask.viewModels.UserViewModel


class MainActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userAdapter = UserAdapter(
            onDeleteClickListener = { user -> userViewModel.delete(user) },
            onCheckBoxClickListener = { user, isChecked ->
                userViewModel.updateCheckedState(user, isChecked)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = userAdapter

        userViewModel.allUsers.observe(this) { users ->
            users?.let { (binding.recyclerView.adapter as UserAdapter).setUsers(it) }
        }

        binding.btnAddUser.setOnClickListener {
            val username = binding.etUsername.text.toString()

            if (username.isNotEmpty()) {
                val user = User(username = username)
                userViewModel.insert(user)
            }
        }
    }
}


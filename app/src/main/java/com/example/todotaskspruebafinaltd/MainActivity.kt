package com.example.todotaskspruebafinaltd

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todotaskspruebafinaltd.apadter.UserAdapter
import com.example.todotaskspruebafinaltd.databinding.MainActivityBinding
import com.example.todotaskspruebafinaltd.model.User
import com.example.todotaskspruebafinaltd.viewModel.UserViewModel

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

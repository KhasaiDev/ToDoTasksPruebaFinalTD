package com.example.ToDoTask.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ToDoTask.model.User
import com.example.ToDoTask.repository.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application)
    private val _allUsers = MutableLiveData<List<User>>()
    val allUsers: LiveData<List<User>> get() = _allUsers

    init {
        loadUsers()
    }

    private fun loadUsers() {
        _allUsers.postValue(repository.getAllUsers())
    }

    fun insert(user: User) {
        repository.insert(user)
        loadUsers()
    }

    fun delete(user: User) {
        repository.delete(user)
        loadUsers()
    }

    fun updateCheckedState(user: User, isChecked: Boolean) {
        user.isChecked = isChecked
        // You can save the checked state in your database if needed
    }
}
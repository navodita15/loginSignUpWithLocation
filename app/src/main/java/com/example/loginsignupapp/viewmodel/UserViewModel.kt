package com.example.loginsignupapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.loginsignupapp.db.User
import com.example.loginsignupapp.db.UserDao
import com.example.loginsignupapp.db.UserRepo
import com.example.loginsignupapp.db.UserRoomDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val allUsers: LiveData<List<User>>
    private val userRepo: UserRepo

    init {
        val userDao: UserDao = UserRoomDB.getUserDatabase(application).userDao()
        userRepo = UserRepo(userDao)
        allUsers = userRepo.getUsers()
    }

    fun insert(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepo.insert(user)
    }


    fun getPassword(emailId: String): LiveData<User> {
        return userRepo.getPassword(emailId)
    }
}
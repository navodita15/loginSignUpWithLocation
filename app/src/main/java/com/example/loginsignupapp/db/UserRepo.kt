package com.example.loginsignupapp.db

import androidx.lifecycle.LiveData

class UserRepo(private val userDao: UserDao) {
    fun getUsers(): LiveData<List<User>> {
        return userDao.getUser()
    }

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    fun getPassword(emailId: String): LiveData<User> {
        return userDao.getPassword(emailId)
    }
}
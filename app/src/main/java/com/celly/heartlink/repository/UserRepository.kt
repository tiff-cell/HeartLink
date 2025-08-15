package com.celly.heartlink.repository

import com.celly.heartlink.data.UserDao
import com.celly.heartlink.model.User



class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }
}
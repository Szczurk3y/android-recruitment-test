package dog.snow.androidrecruittest.model.service

import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.model.RawUser

interface UserRepository {
    fun saveMultipleUsers(users: List<RawUser>)
    fun getAllUsers(): LiveData<List<RawUser>>
    fun clearAllUsers()
}
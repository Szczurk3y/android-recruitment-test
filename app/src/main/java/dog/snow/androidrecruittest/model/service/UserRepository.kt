package dog.snow.androidrecruittest.model.service

import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.model.RawUser

interface UserRepository {
    fun saveUser(user: RawUser)
    fun getAllUsers(): LiveData<List<RawUser>>
    fun clearAllUsers()
}
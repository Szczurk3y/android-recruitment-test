package dog.snow.androidrecruittest.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import dog.snow.androidrecruittest.model.RawUser

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMultipleUsers(users: List<RawUser>)

    @Query("DELETE FROM user_table")
    fun clearUsers()

    @Query("SELECT * FROM user_table ORDER BY name ASC")
    fun getAllUsers(): LiveData<List<RawUser>>

}
package dog.snow.androidrecruittest.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMultiplePhotos(photos: List<RawPhoto>)

    @Query("SELECT * FROM photo_table WHERE title LIKE :title")
    fun findPhotos(title: String): List<RawPhoto>

    @Query("DELETE FROM photo_table")
    fun clearPhotos()

    @Query("SELECT * FROM photo_table WHERE id = :id")
    fun getPhoto(id: Int): LiveData<RawPhoto>

    @Query("SELECT * FROM photo_table")
    fun getAllPhotos(): LiveData<List<RawPhoto>>
}
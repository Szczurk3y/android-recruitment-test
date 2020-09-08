package dog.snow.androidrecruittest.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: RawPhoto)

    @Delete
    fun clearPhotos(vararg album: RawPhoto)

    @Query("SELECT * FROM photo_table ORDER BY title ASC")
    fun getAllPhotos(): LiveData<List<RawPhoto>>
}
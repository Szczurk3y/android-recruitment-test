package dog.snow.androidrecruittest.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import dog.snow.androidrecruittest.model.RawAlbum

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMultipleAlbums(albums: List<RawAlbum>)

    @Query("DELETE FROM album_table ")
    fun clearAlbums()

    @Query("SELECT * FROM album_table ORDER BY title ASC")
    fun getAllAlbums(): LiveData<List<RawAlbum>>
}
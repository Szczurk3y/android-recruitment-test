package dog.snow.androidrecruittest.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import dog.snow.androidrecruittest.model.RawAlbum

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(album: RawAlbum)

    @Delete
    fun clearAlbums(vararg album: RawAlbum)

    @Query("SELECT * FROM album_table ORDER BY title ASC")
    fun getAllAlbums(): LiveData<List<RawAlbum>>
}
package dog.snow.androidrecruittest.model.service

import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.model.RawAlbum

interface AlbumRepository {
    fun saveMultipleAlbums(albums: List<RawAlbum>)
    fun getAllAlbums(): LiveData<List<RawAlbum>>
    fun clearAllAlbums()
    fun getAlbum(id: Int): LiveData<RawAlbum>
}
package dog.snow.androidrecruittest.model.service

import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.model.RawAlbum

interface AlbumRepository {
    fun saveAlbum(album: RawAlbum)
    fun getAllAlbums(): LiveData<List<RawAlbum>>
    fun clearAllAlbums()
}
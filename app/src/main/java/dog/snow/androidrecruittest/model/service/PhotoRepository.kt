package dog.snow.androidrecruittest.model.service

import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.model.RawPhoto

interface PhotoRepository {
    fun savePhoto(photo: RawPhoto)
    fun getAllPhotos(): LiveData<List<RawPhoto>>
    fun clearAllPhotos()
}
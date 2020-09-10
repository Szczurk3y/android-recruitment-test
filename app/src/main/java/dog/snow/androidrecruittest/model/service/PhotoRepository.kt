package dog.snow.androidrecruittest.model.service

import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.model.RawPhoto

interface PhotoRepository {
    fun saveMultiplePhotos(photos: List<RawPhoto>)
    fun getAllPhotos(): LiveData<List<RawPhoto>>
    fun clearAllPhotos()
}
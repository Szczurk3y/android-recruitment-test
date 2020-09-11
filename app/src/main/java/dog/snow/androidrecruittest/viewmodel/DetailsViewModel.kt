package dog.snow.androidrecruittest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.app.SnowDogApplication
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.RawUser
import dog.snow.androidrecruittest.model.room.RoomRepository
import dog.snow.androidrecruittest.model.service.UserRepository

class DetailsViewModel(private val repository: RoomRepository = RoomRepository()): ViewModel() {

    fun getAlbum(id: Int): LiveData<RawAlbum> {
        return repository.getAlbum(id)
    }

    fun getUser(id: Int): LiveData<RawUser> {
        return repository.getUser(id)
    }
}

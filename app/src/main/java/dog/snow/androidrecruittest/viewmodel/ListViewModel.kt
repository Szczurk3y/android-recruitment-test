package dog.snow.androidrecruittest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.app.SnowDogApplication
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.room.RoomRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class ListViewModel(private val repository: RoomRepository = RoomRepository()): ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }
}
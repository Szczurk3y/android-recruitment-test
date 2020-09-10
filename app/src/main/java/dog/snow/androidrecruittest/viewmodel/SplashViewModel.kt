package dog.snow.androidrecruittest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.app.SnowDogApplication
import dog.snow.androidrecruittest.model.*
import dog.snow.androidrecruittest.model.room.AlbumDao
import dog.snow.androidrecruittest.model.room.RoomRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SplashViewModel(
    private val repository: RoomRepository = RoomRepository()
): ViewModel() {

    val disposables = CompositeDisposable()

    val downloadStatus = MutableLiveData<DownloadStatus>()
    val albumsLiveData = MutableLiveData<List<RawAlbum>>()
    val photosLiveData = MutableLiveData<List<RawPhoto>>()

    init {
        repository.clearAllPhotos()
    }

    fun startDownload() {
        downloadStatus.postValue(DownloadStatus.START)

        val photos = JSON.fetchPhotos(100)

        val filteredAlbums = JSON.fetchAlbums()
            .flatMap {
                photos.scan(it) { albums, photos ->
                    photos.mapNotNull { photo ->
                        JSON.filterAlbumForPhoto(albums, photo)
                    }
                }
            }
            .map {
                it.distinctBy { it.id }
            }
        val filteredUsers = JSON.fetchUsers()
            .flatMap {
                filteredAlbums.scan(it) { users, albums ->
                    albums.mapNotNull { album ->
                        JSON.filterUserForAlbum(users, album)
                    }
                }
            }
            .map {
                it.distinctBy { it.id }
            }
            .doOnComplete {
                downloadStatus.postValue(DownloadStatus.END)
            }

        val photosSubscription = photos
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                savePhotos(it)
            }

        val filteredAlbumsSubscription = filteredAlbums
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                saveAlbums(it)
            }
        val filteredUsersSubscription = filteredUsers
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.i("Filtered Users HEJA", it.toString())
            }

        disposables.addAll(photosSubscription, filteredAlbumsSubscription, filteredUsersSubscription)
    }

    fun savePhotos(photos: List<RawPhoto>) {
        repository.saveMultiplePhotos(photos)
        photosLiveData.postValue(photos)
    }

    fun saveAlbums(albums: List<RawAlbum>) {
        albumsLiveData.value = albums
    }

    fun saveUsers() {

    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
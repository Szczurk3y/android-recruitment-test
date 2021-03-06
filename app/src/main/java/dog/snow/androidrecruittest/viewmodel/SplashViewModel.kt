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

    private val disposables = CompositeDisposable()

    val downloadStatus = MutableLiveData<DownloadStatus>()
    val albumsLiveData = MutableLiveData<List<RawAlbum>>()
    val photosLiveData = MutableLiveData<List<RawPhoto>>()
    val usersLiveData = MutableLiveData<List<RawUser>>()

    init {
        disposables.add(Observable.empty<Any>()
            .subscribeOn(Schedulers.io())
            .subscribe {
                repository.clearAllPhotos()
                repository.clearAllAlbums()
                repository.clearAllUsers()
            })

    }

    fun startDownload() {
        downloadStatus.postValue(DownloadStatus.START)

        val photos = JSON.fetchPhotos(100)

        val filteredAlbums = JSON.fetchAlbums()
            .switchMap {
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
            .switchMap {
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
            .subscribe {
                savePhotos(it)
            }

        val filteredAlbumsSubscription = filteredAlbums
            .subscribeOn(Schedulers.io())
            .subscribe {
                saveAlbums(it)
            }
        val filteredUsersSubscription = filteredUsers
            .subscribeOn(Schedulers.io())
            .subscribe {
                saveUsers(it)
            }

        disposables.addAll(photosSubscription, filteredAlbumsSubscription, filteredUsersSubscription)
    }

    private fun savePhotos(photos: List<RawPhoto>) {
        Log.i("elo photo", photos.toString())
        repository.saveMultiplePhotos(photos)
        photosLiveData.postValue(photos)
    }

    private fun saveAlbums(albums: List<RawAlbum>) {
        Log.i("elo album", albums.toString())

        repository.saveMultipleAlbums(albums)
        albumsLiveData.postValue(albums)
    }

    private fun saveUsers(users: List<RawUser>) {
        Log.i("elo users", users.toString())

        repository.saveMultipleUsers(users)
        usersLiveData.postValue(users)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
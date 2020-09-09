package dog.snow.androidrecruittest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.model.DownloadStatus
import dog.snow.androidrecruittest.model.JSON
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.room.AlbumDao
import dog.snow.androidrecruittest.model.room.RoomRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SplashViewModel(
    private val repository: RoomRepository = RoomRepository()
): ViewModel() {

    val disposables = CompositeDisposable()

    val downloadStatus = MutableLiveData<DownloadStatus>()

    fun startDownload() {
        downloadStatus.postValue(DownloadStatus.START)

        val photos = JSON.fetchPhotos(100)
        val albums = JSON.fetchAlbums()
        val users = JSON.fetchUsers()

        val filteredAlbums = albums
            .flatMap {
                photos.scan(it) { albums, photos ->
                    photos.mapNotNull { photo ->
                        JSON.filterAlbumForPhoto(albums, photo)
                    }
                }
            }

        val filteredUsers = users
            .flatMap {
                filteredAlbums.scan(it) { users, albums ->
                    albums.mapNotNull { album ->
                        JSON.filterUserForAlbum(users, album)
                    }
                }
            }
            .doOnComplete {
                downloadStatus.postValue(DownloadStatus.END)
            }

        val photosSubscription = photos
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Log.i("Download photos", it.toString())
            }
        val albumsSubscriptions = albums
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Log.i("Download albums", it.toString())
            }

        val usersSubscription = users
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Log.i("Download users", it.toString())
            }

        val filteredAlbumsSubscription = filteredAlbums
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.distinctBy { it.id }
            }
            .subscribeBy {
                it.forEach { album ->
                    Log.i("Filtered album", album.toString())
                }
            }
        val filteredUsersSubscription = filteredUsers
            .map {
                it.distinctBy { it.id }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                it.forEach { user ->
                    Log.i("Filtered Users", user.toString())
                }
            }

        disposables.addAll(photosSubscription, albumsSubscriptions, usersSubscription, filteredAlbumsSubscription, filteredUsersSubscription)
    }

    fun savePhotos() {

    }

    fun saveAlbums() {

    }

    fun saveUsers() {

    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
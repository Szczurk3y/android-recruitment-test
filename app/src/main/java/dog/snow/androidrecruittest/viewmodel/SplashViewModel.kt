package dog.snow.androidrecruittest.viewmodel

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.model.DownloadStatus
import dog.snow.androidrecruittest.model.JSON
import dog.snow.androidrecruittest.model.RawUser
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SplashViewModel: ViewModel() {
    val disposables = CompositeDisposable()

    val downloadingStatus = MutableLiveData<DownloadStatus>()

    fun startDownload() {
        downloadingStatus.postValue(DownloadStatus.START)
        val photos = JSON.fetchPhotos(100)
        val albums = JSON.fetchAlbums()
        val users = JSON.fetchUsers()
            .doOnComplete {
                downloadingStatus.postValue(DownloadStatus.END)
            }

        val photosSubscription = photos
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Log.i("Downloads", it.map { it.title }.toString())
            }
        val albumsSubscriptions = albums
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Log.i("Downloads", it.map { it.title }.toString())
            }

        val usersSubscription = users
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Log.i("Downloads", it.map { it.name }.toString())
            }
        disposables.addAll(photosSubscription, albumsSubscriptions, usersSubscription)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
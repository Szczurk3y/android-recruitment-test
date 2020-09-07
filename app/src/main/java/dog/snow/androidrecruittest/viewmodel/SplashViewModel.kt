package dog.snow.androidrecruittest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.model.DownloadStatus

class SplashViewModel: ViewModel() {
    val downloadingStatus = MutableLiveData<DownloadStatus>()

    fun startDownload() {

    }
}
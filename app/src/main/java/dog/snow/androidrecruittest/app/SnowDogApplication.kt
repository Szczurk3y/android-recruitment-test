package dog.snow.androidrecruittest.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import dog.snow.androidrecruittest.model.ConnectivityStatus
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.RawUser
import dog.snow.androidrecruittest.model.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@Suppress("DEPRECATION")
class SnowDogApplication : Application() {
    companion object {
        lateinit var database: MyDatabase
        val connectivityLiveData = MutableLiveData<ConnectivityStatus>()
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, MyDatabase::class.java, "my_database")
            .build()

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    Log.i("Connection test", "available")
                    connectivityLiveData.postValue(ConnectivityStatus.AVAILABLE)
                }

                override fun onLost(network: Network?) {
                    Log.i("Connection test", "lost")
                    connectivityLiveData.postValue(ConnectivityStatus.UNAVAILABLE)
                }

                override fun onUnavailable() {
                    Log.i("Connection test", "unavailable")
                    connectivityLiveData.postValue(ConnectivityStatus.UNAVAILABLE)
                }
            })
        }

        val activeNetwork = connectivityManager.activeNetworkInfo;
        if (activeNetwork != null && activeNetwork.isConnected) {
            connectivityLiveData.postValue(ConnectivityStatus.AVAILABLE)

        } else {
            connectivityLiveData.postValue(ConnectivityStatus.UNAVAILABLE)
        }
    }
}
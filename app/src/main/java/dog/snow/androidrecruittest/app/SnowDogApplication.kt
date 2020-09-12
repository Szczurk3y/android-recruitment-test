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

class SnowDogApplication : Application() {
    val connectivityLiveData = MutableLiveData<ConnectivityStatus>()

    companion object {
        lateinit var database: MyDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, MyDatabase::class.java, "my_database")
            .build()

        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    connectivityLiveData.postValue(ConnectivityStatus.AVAILABLE)
                    Log.i("Connection test", "available")
                }

                override fun onLost(network: Network?) {
                    connectivityLiveData.postValue(ConnectivityStatus.UNAVAILABLE)
                    Log.i("Connection test", "unavailable")
                }
            })
        }
    }
}
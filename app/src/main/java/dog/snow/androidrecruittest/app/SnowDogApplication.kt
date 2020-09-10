package dog.snow.androidrecruittest.app

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.RawUser
import dog.snow.androidrecruittest.model.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SnowDogApplication : Application() {
    val subscriptions = CompositeDisposable()

    companion object {
        lateinit var database: MyDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, MyDatabase::class.java, "my_database")
            .allowMainThreadQueries()
            .build()
    }
}
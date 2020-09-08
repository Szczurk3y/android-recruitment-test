package dog.snow.androidrecruittest.app

import android.app.Application
import android.os.Bundle
import androidx.room.Room
import dog.snow.androidrecruittest.model.room.MyDatabase

class SnowDogApplication : Application() {
    companion object {
        lateinit var database: MyDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, MyDatabase::class.java, "my_database")
            .build()
    }
}
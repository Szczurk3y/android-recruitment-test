package dog.snow.androidrecruittest.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.RawUser

@Database(entities = [RawAlbum::class, RawPhoto::class, RawUser::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun photoDao(): PhotoDao
    abstract fun userDao(): UserDao
}
package dog.snow.androidrecruittest.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.RawUser

@Database(entities = [RawUser::class, RawAlbum::class, RawPhoto::class], version = 1)
@TypeConverters(RawUserConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun photoDao(): PhotoDao
    abstract fun userDao(): UserDao
}
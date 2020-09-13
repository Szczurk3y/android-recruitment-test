package dog.snow.androidrecruittest.model.room

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.app.SnowDogApplication
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.RawUser
import dog.snow.androidrecruittest.model.service.AlbumRepository
import dog.snow.androidrecruittest.model.service.PhotoRepository
import dog.snow.androidrecruittest.model.service.UserRepository

class RoomRepository : AlbumRepository, PhotoRepository, UserRepository {
    private val albumDao: AlbumDao = SnowDogApplication.database.albumDao()
    private val photoDao: PhotoDao = SnowDogApplication.database.photoDao()
    private val userDao: UserDao = SnowDogApplication.database.userDao()

    private val allAlbums: LiveData<List<RawAlbum>>
    private val allPhotos: LiveData<List<RawPhoto>>

    init {
        allAlbums = albumDao.getAllAlbums()
        allPhotos = photoDao.getAllPhotos()
    }

    override fun saveMultiplePhotos(photos: List<RawPhoto>) {
        InsertPhotosAsyncTask(photoDao).execute(photos)
    }
    override fun saveMultipleAlbums(albums: List<RawAlbum>) {
        InsertAlbumsAsyncTask(albumDao).execute(albums)
    }
    override fun saveMultipleUsers(users: List<RawUser>) {
        InsertUsersAsyncTask(userDao).execute(users)
    }

    override fun getAllAlbums(): LiveData<List<RawAlbum>> = allAlbums
    override fun getAllPhotos(): LiveData<List<RawPhoto>> = allPhotos

    override fun getPhoto(id: Int): LiveData<RawPhoto> = photoDao.getPhoto(id)
    override fun getUser(id: Int): LiveData<RawUser> = userDao.getUser(id)
    override fun getAlbum(id: Int): LiveData<RawAlbum> = albumDao.getAlbum(id)

    override fun findPhotos(title: String): List<RawPhoto>? = photoDao.findPhotos(title)

    override fun clearAllAlbums() = albumDao.clearAlbums()
    override fun clearAllPhotos() = photoDao.clearPhotos()
    override fun clearAllUsers() = userDao.clearUsers()

    private class InsertPhotosAsyncTask(private val dao: PhotoDao ): AsyncTask<List<RawPhoto>, Void, Unit>() {
        override fun doInBackground(vararg photo: List<RawPhoto>) {
            dao.insertMultiplePhotos(photo[0])
        }
    }

    private class InsertAlbumsAsyncTask(private val dao: AlbumDao ): AsyncTask<List<RawAlbum>, Void, Unit>() {
        override fun doInBackground(vararg photo: List<RawAlbum>) {
            dao.insertMultipleAlbums(photo[0])
        }
    }

    private class InsertUsersAsyncTask(private val dao: UserDao ): AsyncTask<List<RawUser>, Void, Unit>() {
        override fun doInBackground(vararg photo: List<RawUser>) {
            dao.insertMultipleUsers(photo[0])
        }
    }
}
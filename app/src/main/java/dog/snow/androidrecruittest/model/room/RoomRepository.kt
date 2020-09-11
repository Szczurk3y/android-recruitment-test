package dog.snow.androidrecruittest.model.room

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

    override fun saveMultiplePhotos(photos: List<RawPhoto>) = photoDao.insertMultiplePhotos(photos)
    override fun saveMultipleAlbums(albums: List<RawAlbum>) = albumDao.insertMultipleAlbums(albums)
    override fun saveMultipleUsers(users: List<RawUser>) = userDao.insertMultipleUsers(users)

    override fun getAllAlbums(): LiveData<List<RawAlbum>> = allAlbums
    override fun getAllPhotos(): LiveData<List<RawPhoto>> = allPhotos

    override fun getPhoto(id: Int): LiveData<RawPhoto> = photoDao.getPhoto(id)
    override fun getUser(id: Int): LiveData<RawUser> = userDao.getUser(id)
    override fun getAlbum(id: Int): LiveData<RawAlbum> = albumDao.getAlbum(id)

    override fun clearAllAlbums() = albumDao.clearAlbums()
    override fun clearAllPhotos() = photoDao.clearPhotos()
    override fun clearAllUsers() = userDao.clearUsers()
}
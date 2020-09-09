package dog.snow.androidrecruittest.model

import android.util.Log
import io.reactivex.Observable


object JSON {
    const val API = "https://jsonplaceholder.typicode.com/"
    const val ALBUMS_ENDPOINT = "albums"
    const val PHOTOS_ENDPOINT = "photos"
    const val USERS_ENDPOINT = "users"

    private val json by lazy {
        JsonAPI.create()
    }

    fun fetchAlbums(): Observable<List<RawAlbum>> {
        return json.fetchAlbums()
    }

    fun fetchPhotos(limit: Int): Observable<List<RawPhoto>> {
        return json.fetchPhotos(limit)
    }

    fun fetchUsers(): Observable<List<RawUser>> {
        return json.fetchUsers()
    }

    fun filterAlbumForPhoto(albums: List<RawAlbum>, photo: RawPhoto): RawAlbum? {
        albums.forEach { album ->
            if (album.id == photo.albumId) {
                return album
            }
        }
        return null
    }

    fun filterUserForAlbum(users: List<RawUser>, album: RawAlbum): RawUser? {
        users.forEach { user ->
            if (user.id == album.userId) {
                return user
            }
        }
        return null
    }
}
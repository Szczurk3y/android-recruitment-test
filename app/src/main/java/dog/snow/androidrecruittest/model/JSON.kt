package dog.snow.androidrecruittest.model

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

    fun fetchPhotos(): Observable<List<RawPhoto>> {
        return json.fetchPhotos()
    }

    fun fetchUsers(): Observable<List<RawUser>> {
        return json.fetchUsers()
    }
}
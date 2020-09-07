package dog.snow.androidrecruittest.model

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface JsonAPI {
    companion object {
        fun create(): JsonAPI {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(JSON.API)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(JsonAPI::class.java)
        }
    }

    @GET(JSON.ALBUMS_ENDPOINT)
    fun fetchAlbums(): Observable<List<RawAlbum>>

    @GET(JSON.PHOTOS_ENDPOINT)
    fun fetchPhotos(): Observable<List<RawPhoto>>

    @GET(JSON.USERS_ENDPOINT)
    fun fetchUsers(): Observable<List<RawUser>>
}
package dog.snow.androidrecruittest.view.content

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.app.SnowDogApplication
import dog.snow.androidrecruittest.model.ConnectivityStatus
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.RawUser
import dog.snow.androidrecruittest.view.adapter.PhotosAdapter
import dog.snow.androidrecruittest.viewmodel.ListViewModel
import io.reactivex.Observable
import kotlinx.android.synthetic.main.layout_search.*
import kotlinx.android.synthetic.main.list_activity.*

class ListActivity : AppCompatActivity() {

    private lateinit var viewmodel: ListViewModel
    private lateinit var adapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.list_activity)

        val photos = intent.getParcelableArrayListExtra<RawPhoto>(RawPhoto.PHOTO_KEY)!!.toMutableList()
        val albums = intent.getParcelableArrayListExtra<RawAlbum>(RawAlbum.ALBUM_KEY)!!.toMutableList()

        rv_items.layoutManager = LinearLayoutManager(this)
        adapter = PhotosAdapter(photos, albums)

        adapter.setOnPhotoTapListener { photo ->
            val fragment = DetailsFragment.newInstance(photo)
            fragment.show(supportFragmentManager, "Details Fragment")
        }

        viewmodel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        viewmodel.searchLiveData.observe(this, Observer {
            adapter.update(it)
        })

        viewmodel.createTextChangeObservable(search)

        SnowDogApplication.connectivityLiveData.observe(this, Observer { status ->
            if (status == ConnectivityStatus.AVAILABLE) {
                tv_offline_mode.visibility = View.GONE
            } else {
                tv_offline_mode.visibility = View.VISIBLE
            }
        })

        rv_items.adapter = adapter
    }
}
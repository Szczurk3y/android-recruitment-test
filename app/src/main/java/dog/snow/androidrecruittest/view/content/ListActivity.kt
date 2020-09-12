package dog.snow.androidrecruittest.view.content

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dog.snow.androidrecruittest.R
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

        adapter = PhotosAdapter(photos, albums)

        rv_items.layoutManager = LinearLayoutManager(this)
        rv_items.adapter = adapter

        adapter.setOnPhotoTapListener { photo ->
            val fragment = DetailsFragment.newInstance(photo)
            fragment.show(supportFragmentManager, "Details Fragment")
        }

        viewmodel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        viewmodel.createTextChangeObservable(search)
    }
}
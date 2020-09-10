package dog.snow.androidrecruittest.view.content

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.view.adapter.PhotosAdapter
import dog.snow.androidrecruittest.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.list_activity.*

class ListActivity() : AppCompatActivity() {

    private lateinit var viewmodel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.list_activity)

        val photos = intent.getParcelableArrayListExtra<RawPhoto>(RawPhoto.PHOTO_KEY)!!.toList()
        val albums = intent.getParcelableArrayListExtra<RawAlbum>(RawAlbum.ALBUM_KEY)!!.toList()

        rv_items.layoutManager = LinearLayoutManager(this)
        rv_items.adapter = PhotosAdapter(photos.toMutableList(), albums.toMutableList())

        viewmodel = ViewModelProviders.of(this).get(ListViewModel::class.java)
    }
}
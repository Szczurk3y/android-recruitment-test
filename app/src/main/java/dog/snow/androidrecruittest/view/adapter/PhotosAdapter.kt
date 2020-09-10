package dog.snow.androidrecruittest.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import kotlinx.android.synthetic.main.list_item.view.*

class PhotosAdapter(
    private val photosList: MutableList<RawPhoto>,
    private val albumsList: MutableList<RawAlbum>
): RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.photo_title.text = photosList[position].title
        holder.album_title.text = if (photosList[position].albumId == 1) {
            "quidem molestiae enim"
        } else {
            "sunt qui excepturi placeat culpa"
        }

        Picasso.get()
            .load(photosList[position].thumbnailUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.thumbnail)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val thumbnail = itemView.iv_thumb
        val photo_title = itemView.tv_photo_title
        val album_title = itemView.tv_album_title
    }

}
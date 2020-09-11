package dog.snow.androidrecruittest.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.RawUser
import kotlinx.android.synthetic.main.list_item.view.*

class PhotosAdapter(
    private val photosList: MutableList<RawPhoto>,
    private val albumsList: MutableList<RawAlbum>
): RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private var listener: ((RawPhoto) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    fun swapData(photos: List<RawPhoto>) {
        this.photosList.clear()
        this.photosList.addAll(photos)
        notifyDataSetChanged()
    }

    fun setOnPhotoTapListener(listener: ((RawPhoto) -> Unit)? = null) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.photo_title.text = photosList[position].title
        albumsList.forEach {album ->
            if (photosList[position].albumId == album.id) {
                holder.album_title.text = album.title
            }
        }

        Picasso.get()
            .load(photosList[position].thumbnailUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.thumbnail)


        holder.itemView.setOnClickListener {
            listener?.invoke(photosList[position])
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val thumbnail = itemView.iv_thumb
        val photo_title = itemView.tv_photo_title
        val album_title = itemView.tv_album_title
    }

}
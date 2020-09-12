package dog.snow.androidrecruittest.view.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.RawUser
import dog.snow.androidrecruittest.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment : DialogFragment(),  Toolbar.OnMenuItemClickListener {
    private lateinit var detailViewModel: DetailsViewModel
    private lateinit var photo: RawPhoto
    private lateinit var album: RawAlbum
    private lateinit var user: RawUser

    companion object {
        fun newInstance(photo: RawPhoto): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle().apply {
                putParcelable(RawPhoto.PHOTO_KEY, photo)
            }
            fragment.arguments = args

            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.AppTheme_Fragment

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_Fragment)
        detailViewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsToolbar.setNavigationOnClickListener {
            dismiss()
        }

        detailsToolbar.setOnMenuItemClickListener(this)

        arguments?.getParcelable<RawPhoto>(RawPhoto.PHOTO_KEY)?.let {
            photo = it
        }

        detailViewModel.getAlbum(photo.albumId).observe(viewLifecycleOwner, Observer { album ->
            this.album = album
            detailViewModel.getUser(album.userId).observe(viewLifecycleOwner, Observer { user ->
                this.user = user
                displayInfo()
            })
        })

    }

    private fun displayInfo() {
        // Load the image
        Picasso.get()
            .load(photo.thumbnailUrl)
            .error(R.drawable.ic_placeholder)
            .placeholder(R.drawable.ic_placeholder)
            .into(iv_photo)

        // Load the photo info
        tv_photo_title.text = photo.title
        tv_album_title.text = album.title
        tv_email.text = user.email
        tv_phone.text = user.phone


    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return true
    }


}
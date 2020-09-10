package dog.snow.androidrecruittest.view.content

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.model.DownloadStatus
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.RawUser
import dog.snow.androidrecruittest.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : AppCompatActivity() {
    private lateinit var viewmodel: SplashViewModel
    private var albums = listOf<RawAlbum>()
    private var photos = listOf<RawPhoto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        makeFullScreen()
        setContentView(R.layout.splash_activity)
        startAnim()

        viewmodel = ViewModelProviders.of(this).get(SplashViewModel::class.java)

        viewmodel.downloadStatus.observe(this, Observer { status ->
            if(status == DownloadStatus.END) {
                val intent = Intent(this, ListActivity::class.java)
                intent.putParcelableArrayListExtra(RawAlbum.ALBUM_KEY, ArrayList(albums))
                intent.putParcelableArrayListExtra(RawPhoto.PHOTO_KEY, ArrayList(photos))
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        })

        viewmodel.albumsLiveData.observe(this, Observer { albums: List<RawAlbum> ->
            this.albums = albums
        })

        viewmodel.photosLiveData.observe(this, Observer { photos: List<RawPhoto> ->
            this.photos = photos
        })

        viewmodel.startDownload()

    }

    private fun makeFullScreen() {
        // Remove Title
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Make Fullscreen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // Hide the toolbar
        supportActionBar?.hide()
    }

    private fun startAnim() {
        progressbar.show()
        val ivLogoAnim = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        val ivLogoTekstAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        iv_logo_sd_symbol.startAnimation(ivLogoAnim)
        iv_logo_sd_text.startAnimation(ivLogoTekstAnim)
    }



    private fun showError(errorMessage: String?) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ -> /*tryAgain()*/ }
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> finish() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }
}
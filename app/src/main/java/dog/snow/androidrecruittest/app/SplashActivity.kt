package dog.snow.androidrecruittest.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.view.content.ListActivity
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : AppCompatActivity() {

    private val DELAY: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        makeFullScreen()
        setContentView(R.layout.splash_activity)
        makeAnim()

        // Using a handler to delay loading the MainActivity
        Handler().postDelayed({
            startActivity(Intent(this, ListActivity::class.java))

            // Animate the loading of new activity
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            finish()
        }, DELAY)

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

    private fun makeAnim() {
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
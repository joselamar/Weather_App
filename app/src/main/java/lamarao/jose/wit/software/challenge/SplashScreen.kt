package lamarao.jose.wit.software.challenge

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class SplashScreen : AppCompatActivity() {

    private val LOCATION_REQUEST_CODE = 11

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        getUserPermission()

    }

    //ask permission to user to use location
    private fun getUserPermission() {
        //check location permissions if false requestsPermission else lauches activity by checking internet
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Permission to access the location is required for this app to show your current weather. If you disable this permission, other location will be selected")
                    .setTitle("Permission required")
                builder.setPositiveButton("OK") { dialog, id ->
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
                }
                val dialog = builder.create()
                dialog.show()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            }
        } else {
            checkInternet()
        }
    }

    //Loads activity after user decision
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_REQUEST_CODE -> checkInternet()
        }
    }

    // calls internet connection, if true loads main activity else recreate activity on retry or terminate on cancel
    private fun checkInternet() {
        hasInternetConnection().subscribe { hasInternet ->
            if (hasInternet) {
                Handler().postDelayed({
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 500)
            } else {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Make sure that WI-FI or mobile data is turned on, then try again")
                    .setCancelable(false)
                    .setPositiveButton("Retry", { dialog, id -> recreate() })
                    .setNegativeButton("Cancel", { dialog, id -> finish() })
                val alert = dialogBuilder.create()
                alert.setTitle("No Internet Connection")
                alert.setIcon(R.mipmap.ic_launcher)
                alert.show()
            }
        }
    }

    //checks internet connection
    fun hasInternetConnection(): Single<Boolean> {
        return Single.fromCallable {
            try {
                // Connect to Google DNS to check for connection
                val timeoutMs = 1500
                val socket = Socket()
                val socketAddress = InetSocketAddress("8.8.8.8", 53)

                socket.connect(socketAddress, timeoutMs)
                socket.close()

                true
            } catch (e: IOException) {
                false
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}


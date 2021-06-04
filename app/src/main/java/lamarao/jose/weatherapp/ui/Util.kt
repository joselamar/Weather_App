package lamarao.jose.weatherapp.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


fun handleTime(timestamp: Long,pattern: String) : String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp * 1000L
    val sdf = SimpleDateFormat(pattern, Locale.US)
    return sdf.format(Date(calendar.timeInMillis))
}
fun handleWindMetric(selectedUnit: String?): String {
    return if (selectedUnit == "metric") " m/s" else " Mi/h"
}

fun handleTempMetric(selectedUnit: String?) : String {
    return if (selectedUnit == "metric") "C" else "F"
}

fun windDirection(deg: Double): String {
    var degree = deg
    val sectors = arrayOf("N","NE","E","SE","S","SW","W","NW")
    degree += 22.5
    if (degree < 0)
        degree = 360 - abs(degree) % 360
    else
        degree %= 360
    val which = (degree / 45).toInt()
    return sectors[which]
}


fun Context.hasPermission(permission: String): Boolean {

    // Background permissions didn't exit prior to Q, so it's approved by default.
    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
        return true
    }

    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}
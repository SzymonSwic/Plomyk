package szymon.swic.plomyk.core.permissions

import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import szymon.swic.plomyk.R

object PermissionsHelper {

    fun requestPermissions(activity: Activity, type: PermissionType) =
        ActivityCompat.requestPermissions(activity, type.permissions, type.requestCode)

    fun hasPermission(activity: Activity, perm: String): Boolean =
        ContextCompat.checkSelfPermission(activity, perm) == PackageManager.PERMISSION_GRANTED

    fun hasPermissionBeenGranted(results: IntArray) =
        !(results.isEmpty() || results[0] != PackageManager.PERMISSION_GRANTED)

    fun showNoPermissionInfo(activity: Activity) = Toast.makeText(
        activity,
        activity.resources.getString(R.string.no_required_permissions),
        Toast.LENGTH_SHORT
    ).show()
}

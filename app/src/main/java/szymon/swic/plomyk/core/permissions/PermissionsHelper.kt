package szymon.swic.plomyk.core.permissions

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import szymon.swic.plomyk.R

object PermissionsHelper {

    fun hasPermission(activity: Activity, perm: String): Boolean =
        ContextCompat.checkSelfPermission(activity, perm) == PackageManager.PERMISSION_GRANTED

    fun hasPermissionBeenGranted(results: IntArray) =
        !(results.isEmpty() || results[0] != PackageManager.PERMISSION_GRANTED)

    fun showNoPermissionInfo(activity: Activity) = Toast.makeText(
        activity,
        activity.resources.getString(R.string.no_required_permissions),
        Toast.LENGTH_SHORT
    ).show()

    fun isEveryPermissionGranted(results: IntArray) =
        results.all { it == PackageManager.PERMISSION_GRANTED }

    fun handlePermissions(activity: Activity, type: PermissionType, action: () -> Unit) {
        if (checkPermissions(activity, type)) action()
        else requestPermissions(activity, type)
    }

    fun checkPermissions(activity: Activity, type: PermissionType): Boolean =
        type.permissions.all { checkPermission(activity, it) }

    private fun checkPermission(activity: Activity, permission: String) =
        ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions(activity: Activity, type: PermissionType) {
        val shouldShowRationaleForAny = shouldShowRationaleForAny(activity, type.permissions)

        if (canRequestPermissions() && !shouldShowRationaleForAny) {
            ActivityCompat.requestPermissions(activity, type.permissions, type.requestCode)
        } else {
            showSettingsDialog(activity, type)
        }
    }

    private fun shouldShowRationaleForAny(activity: Activity, permissions: Array<String>): Boolean =
        permissions.map { shouldShowRationale(activity, it) }
            .any { it }

    private fun shouldShowRationale(activity: Activity, permission: String) =
        ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)

    private fun showSettingsDialog(activity: Activity, type: PermissionType) =
        AlertDialog.Builder(activity)
            .setMessage(activity.getString(R.string.permissions_go_to_settings, type.warningLabel))
            .setPositiveButton(activity.getString(R.string.settings)) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val packageName = activity.packageName ?: ""
                val uri: Uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                activity.startActivity(intent)
            }
            .setNegativeButton(activity.getString(R.string.cancel), null)
            .create()
            .show()

    /**
     * Checks if runtime permissions can be requested
     */
    private fun canRequestPermissions() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}

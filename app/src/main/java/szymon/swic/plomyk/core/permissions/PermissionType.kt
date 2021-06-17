package szymon.swic.plomyk.core.permissions

import android.Manifest

enum class PermissionType(
    val requestCode: Int,
    val permissions: Array<String>
) {
    RECORD_AUDIO(
        requestCode = 200,
        permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    ),
    CAMERA(
        requestCode = 1650,
        permissions = arrayOf(Manifest.permission.CAMERA)
    );
}
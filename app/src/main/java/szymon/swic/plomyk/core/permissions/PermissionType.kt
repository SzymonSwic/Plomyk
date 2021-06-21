package szymon.swic.plomyk.core.permissions

import android.Manifest

enum class PermissionType(
    val permissions: Array<String>,
    val requestCode: Int,
    val warningLabel: String
) {
    RECORD_AUDIO(
        permissions = arrayOf(Manifest.permission.RECORD_AUDIO),
        requestCode = 200,
        warningLabel = "nagrywania dźwięku"
    ),
    CAMERA(
        permissions = arrayOf(Manifest.permission.CAMERA),
        requestCode = 1650,
        warningLabel = "aparatu"
    );
}

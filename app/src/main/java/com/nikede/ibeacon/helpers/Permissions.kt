package com.nikede.ibeacon.helpers

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.nikede.ibeacon.R

class Permissions(context: Context) {
    companion object {
        val PERMISSION_REQUEST_FINE_LOCATION = 1

        fun getPermissions(activity: Activity, permissionIds: Array<Int>) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permissionIds.forEach {
                    when (it) {
                        PERMISSION_REQUEST_FINE_LOCATION -> {
                            if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                activity.requestPermissions(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ),
                                    PERMISSION_REQUEST_FINE_LOCATION
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
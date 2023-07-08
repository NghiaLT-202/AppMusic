package com.example.appmusic.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Build
import android.os.StrictMode
import androidx.annotation.RequiresApi
import java.io.IOException
import java.io.InputStream
import java.net.URL

object Utils {
    fun pxToDp(px: Float): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun isAtLeastSdkVersion(versionCode: Int): Boolean {
        return Build.VERSION.SDK_INT >= versionCode
    }

    fun getIcon(src: String?): Bitmap? {
        var bitmap: Bitmap? = null
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            val url = URL(src)
            bitmap = BitmapFactory.decodeStream(url.content as InputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun setListApi(): List<String> {
        val list: MutableList<String> = ArrayList()
        list.add("AIzaSyCbNNRLk3Pvxw5ZbinDwL1PTeydWSlsEss")
        list.add("AIzaSyCyrI7vvvmy94ISqLelhA-WZvGlIkmeJCc")
        list.add("AIzaSyBYZBsu1ryIy6Yei4njML7w_pu9eGk69Tc")
        list.add("AIzaSyAxSma_MMh9WuQQULrmXscvSVQuvtthdMY")
        list.add("AIzaSyCrX6RDsVucuNNeewOIOgOxwd9UJg9ZaoU")
        list.add("AIzaSyCcB3eiJ9QTZtgV60_jeuaRn0K0HStMXWw")
        list.add("AIzaSyBerVzUR5s4OiFU-waj5noE3TGJKZFnPaY")
        list.add("AIzaSyBMUjzN4CKSHtxuFKguTksxpK8MIyg3I2Y")
        return list
    }

    object NetworkUtil {
        const val TYPE_NOT_CONNECTED = 0
        const val TYPE_WIFI = 1
        const val TYPE_MOBILE = 2
        @RequiresApi(api = Build.VERSION_CODES.M)
        fun getConnectivityStatus(context: Context): Int {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nc = cm.getNetworkCapabilities(cm.activeNetwork)
            val activeNetwork = cm.activeNetworkInfo
            if (null != activeNetwork) {
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    return TYPE_WIFI
                }
                if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) return TYPE_MOBILE
            }
            return TYPE_NOT_CONNECTED
        }
    }
}
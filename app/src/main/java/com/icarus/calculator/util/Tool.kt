package com.icarus.calculator.util

import a.icarus.utils.Logger
import a.icarus.utils.ThreadManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.json.JSONArray

fun Context.dp2px(dp: Float) =
    (resources.displayMetrics.density * dp + 0.5f).toInt()

fun Context.sp2px(sp: Float) =
    (resources.displayMetrics.scaledDensity * sp + 0.5f).toInt()

fun <T> T.mainThread(run: () -> Unit) {
    ThreadManager.runOnUiThread(run)
}

fun <T> T.subThread(run: () -> Unit) {
    ThreadManager.runOnThreadPool(run)
}

fun <T> T.log() {
    Logger.t(this)
}

fun <T> T.print() {
    println(this)
}

fun AppCompatActivity.startActivity(clazz: Class<out Activity>) {
    val intent = Intent(this, clazz)
    startActivity(intent)
}

fun Fragment.startActivity(clazz: Class<out Activity>) {
    val intent = Intent(this.context, clazz)
    startActivity(intent)
}

fun main() {

}


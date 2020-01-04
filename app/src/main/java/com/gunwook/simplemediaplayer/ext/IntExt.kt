package com.gunwook.simplemediaplayer.ext

import android.content.res.Resources
import java.util.*

fun Int.timeFormat() : String {
    val hour = this / 3600
    val minute = (this % 3600) / 60
    val second =  (this % 3600) % 60
    if(hour != 0){
        return String.format(Locale.KOREA , "%2d:%2d:%02d" , hour, minute , second).replace(" " ,"")
    }else
        return String.format(Locale.KOREA , "%d:%02d" , minute , second).replace(" " ,"")
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
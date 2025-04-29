package com.mobily.bugit.ui.utils

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    fun getCurrentDateFormatted():String{
        val formatter = SimpleDateFormat("dd-MM-yy", Locale.getDefault())
        return formatter.format(Date())
    }
}
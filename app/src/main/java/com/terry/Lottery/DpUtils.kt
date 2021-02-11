package com.terry.Lottery

import android.content.Context


object DpUtils {


    fun dp2px(context: Context, value: Float): Float {
        return context.resources.displayMetrics.density * value + 0.5F
    }
}
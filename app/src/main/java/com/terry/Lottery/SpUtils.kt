package com.terry.Lottery

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils

object SpUtils {
    private var sp: SharedPreferences? = null
    private var defaultValue = "一等奖#二等奖#三等奖#四等奖#五等奖#六等奖#无奖"
    fun getAll(context: Context): MutableList<String> {
        initSp(context)
        val value = sp!!.getString("list", defaultValue) as String
        if (TextUtils.isEmpty(value.replace(" ", ""))) {
            return mutableListOf()
        }
        return value.split("#").filter { !TextUtils.isEmpty(it) }.toMutableList()
    }

    fun setAll(context: Context, all: MutableList<String>) {
        initSp(context)
        sp!!.edit().putString("list", all.joinToString(separator = "#")).apply()
    }

    fun addOne(context: Context, one: String) {
        initSp(context)
        val value = sp!!.getString("list", defaultValue) as String
        sp!!.edit().putString("list", value + "#" + one).apply()
    }


    private fun initSp(context: Context) {
        if (sp != null) {
            return
        }
        sp = context.getSharedPreferences("test.xml", Context.MODE_PRIVATE)
    }
}
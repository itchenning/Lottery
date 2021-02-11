package com.terry.Lottery

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_list.*
import org.w3c.dom.Text

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        addBtn.setOnClickListener { addOne() }
        clearBtn.setOnClickListener { clearAll() }
        setList()
    }

    private fun clearAll() {
        SpUtils.setAll(this, mutableListOf())
        while (wrapper.childCount > 2) {
            wrapper.removeViewAt(2)
        }
    }

    private fun setList() {
        val list = SpUtils.getAll(this)
        for (one in list) {
            wrapper.addView(createTextView(one))
        }
    }

    private fun createTextView(text: String): TextView {
        val textView = TextView(this)
        val params = LinearLayout.LayoutParams(-1, DpUtils.dp2px(this, 50F).toInt())
        params.setMargins(0, DpUtils.dp2px(this, 8F).toInt(), 0, 0)
        textView.setBackgroundColor(Color.parseColor("#F1F1F1"))
        textView.setTextColor(Color.BLACK)
        textView.setTextSize(DpUtils.dp2px(this, 14f))
        textView.gravity = Gravity.CENTER
        textView.text = text
        textView.layoutParams = params
        return textView
    }

    private fun addOne() {
        val input = inputEt.text.toString()
        if (TextUtils.isEmpty(input)) {
            Toast.makeText(this, "输入错误", Toast.LENGTH_SHORT).show()
            return
        }
        SpUtils.addOne(this, input)
        wrapper.addView(createTextView(input))
    }
}
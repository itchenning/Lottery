package com.terry.Lottery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn.setOnClickListener {
            go()
        }
        setBtn.setOnClickListener { startActivity(Intent(this, ListActivity::class.java)) }
    }

    override fun onResume() {
        super.onResume()
        lotteryView.setItems(SpUtils.getAll(this))
    }

    private fun go() {
        lotteryView.go()
    }
}
package com.example.a7minutesworkout.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minutesworkout.R
import kotlinx.android.synthetic.main.activity_history.*


class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbar_history_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}
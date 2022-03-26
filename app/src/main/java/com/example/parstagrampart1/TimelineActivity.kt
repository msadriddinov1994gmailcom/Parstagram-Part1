package com.example.parstagrampart1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

lateinit var client: LoginActivity
lateinit var rvPosts: RecyclerView
lateinit var rvAdapter: PostAdapter
class TimelineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

    }
}
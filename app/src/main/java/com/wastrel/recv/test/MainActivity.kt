package com.wastrel.recv.test

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wastrel.recv.test.databinding.ActivityMainBinding

/**
 * @date 2022/11/7 19:24
 * @description
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnSingle.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, RecvActivity::class.java).apply {
                        putExtra("type", "single")
                    }
                )
            }
            btnMulti.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, RecvActivity::class.java).apply {
                        putExtra("type", "multi")
                    }
                )
            }
            btnGrid.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, RecvActivity::class.java).apply {
                        putExtra("type", "grid")
                    }
                )
            }
        }
    }
}

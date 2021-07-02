package com.tslax.tesla_xprogrampl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tslax.tesla_xprogrampl.databinding.ActivityDisclamerBinding

class DisclamerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDisclamerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisclamerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.nextButton.setOnClickListener{
            val i = Intent(this@DisclamerActivity,RegistrationActivity::class.java)
            startActivity(i)
        }
    }
}
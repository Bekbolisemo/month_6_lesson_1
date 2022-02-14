package com.example.month_6_lesson_1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.month_6_lesson_1.databinding.ActivityMain2Binding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.getStringExtra(MainActivity.KEY_SECOND_ACTIVITY_IMAGE) != null) {
            Glide.with(binding.image).load(intent.getStringExtra(MainActivity.KEY_SECOND_ACTIVITY_IMAGE)).circleCrop().into(binding.image)
        }
        if (intent.getStringExtra(MainActivity.KEY_SECOND_ACTIVITY_PUSH) != null)
        binding.enterText.setText(intent.getStringExtra(MainActivity.KEY_SECOND_ACTIVITY_PUSH))
        send()

    }

    private fun send() {
        binding.btnSend.setOnClickListener {
            if (binding.enterText.text.isEmpty()){
                Toast.makeText(
                    this,
                    getString(R.string.text_empty),
                    Toast.LENGTH_LONG
                ).show()
            }else {
                setResult(
                    RESULT_OK,
                    Intent().putExtra(
                        MainActivity.KEY_MAIN_ACTIVITY_PUSH,
                        binding.enterText.text.toString()
                    )
                )
                finish()
            }
        }
    }
}
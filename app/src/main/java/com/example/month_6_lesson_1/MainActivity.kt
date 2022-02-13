package com.example.month_6_lesson_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.month_6_lesson_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var registerForActivityResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        send()
    }

    companion object {
        const val KEY_MAIN_ACTIVITY_PUSH = "key_main_push"
        const val KEY_SECOND_ACTIVITY_PUSH = "key_second_push"
    }

    private fun send() {
        binding.btnSend.setOnClickListener {
            if (binding.enterText.text.isEmpty()) {
                Toast.makeText(
                    this,
                    getString(R.string.text_empty),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Intent(this, SecondActivity::class.java).apply {
                    putExtra(KEY_SECOND_ACTIVITY_PUSH, binding.enterText.text.toString())
                    registerForActivityResult.launch(this)
                }

            }

        }
        registerForActivityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
            binding.enterText.setText(result.data?.getStringExtra(KEY_MAIN_ACTIVITY_PUSH))
        }
    }
}
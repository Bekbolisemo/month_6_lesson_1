package com.example.month_6_lesson_1


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.month_6_lesson_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var registerForActivityResult: ActivityResultLauncher<Intent>

    private var pUri: Uri? = null

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        send()
    }

    companion object {
        const val KEY_SAVE_TEXT = "KEY_SAVE_TEXT"
        const val KEY_SAVE_IMAGE = "KEY_SAVE_IMAGE"
        const val KEY_APP_PREFERENCES = "KEY_APP_PREFERENCES"
        const val KEY_MAIN_ACTIVITY_PUSH = "key_main_push"
        const val KEY_SECOND_ACTIVITY_PUSH = "key_second_push"
        const val KEY_SECOND_ACTIVITY_IMAGE = "key_push_image"
    }

    @SuppressLint("CommitPrefEdits")
    private fun send() {

        preferences = getSharedPreferences(KEY_APP_PREFERENCES,Context.MODE_PRIVATE)

        binding.enterText.setText(preferences.getString(KEY_SAVE_TEXT,""))
        Glide.with(binding.imageUser).load(preferences.getString(KEY_SAVE_IMAGE,R.drawable.ic_user.toString())).circleCrop().into(binding.imageUser)

        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->
                preferences.edit().putString(KEY_SAVE_IMAGE, uri.toString()).apply()
                pUri = uri
                Glide.with(binding.imageUser).load(uri).circleCrop().into(binding.imageUser)

            }
        binding.btnRecycler.setOnClickListener{
            val intent = Intent(this,RecyclerActivity::class.java).apply {
                putExtra(KEY_MAIN_ACTIVITY_PUSH,binding.enterText.text.toString())
            }
            startActivity(intent)
        }
        binding.imageUser.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setNegativeButton(getString(R.string.download)) { _, _ ->
                pUri = null
                getContent.launch(getString(R.string.image_inquiry))
            }
            builder.setNeutralButton(getString(R.string.send)) { _, _ ->
                val intent = Intent(this, SecondActivity::class.java).apply {
                    if (pUri != null) {
                        putExtra(KEY_SECOND_ACTIVITY_IMAGE, pUri.toString())
                    }
                }
                startActivity(intent)
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        binding.enterText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                preferences.edit().putString(KEY_SAVE_TEXT,s.toString()).apply()
            }

        })
        binding.btnSend.setOnClickListener {
            if (binding.enterText.text.isEmpty()) {
                Toast.makeText(
                    this,
                    getString(R.string.text_empty),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (pUri == null) {
                    Intent(this, SecondActivity::class.java).apply {
                        putExtra(KEY_SECOND_ACTIVITY_PUSH, binding.enterText.text.toString())
                        registerForActivityResult.launch(this)
                    }
                } else {
                    Intent(this, SecondActivity::class.java).apply {
                        putExtra(KEY_SECOND_ACTIVITY_IMAGE, pUri.toString())
                        putExtra(KEY_SECOND_ACTIVITY_PUSH, binding.enterText.text.toString())
                        registerForActivityResult.launch(this)
                    }
                }

            }

        }
        registerForActivityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            binding.enterText.setText(result.data?.getStringExtra(KEY_MAIN_ACTIVITY_PUSH))
        }
    }


}
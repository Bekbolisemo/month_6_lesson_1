package com.example.month_6_lesson_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.month_6_lesson_1.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = RecyclerAdapter(addList())

    }

    private fun addList(): List<String> {
        val data = mutableListOf<String>()
        val intent = intent.getStringExtra(MainActivity.KEY_MAIN_ACTIVITY_PUSH)
        (0..90).forEach { i -> data.add("$i $intent") }
        return data
    }

    class RecyclerAdapter(private val name:List<String>):
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val textView: TextView = itemView.findViewById(R.id.text)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_item,
                parent,
                false
            )
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = name[position]
        }

        override fun getItemCount() = name.size
    }
}
package com.example.devnotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.devnotes.R
import com.example.devnotes.entity.Note

class NoteAdapter(private val NoteList: List<Note> = ArrayList()) : RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val body: TextView = view.findViewById(R.id.body)

        fun bindItems(item: Note) {
            title.text = item.title
            body.text = item.body

            itemView.setOnClickListener {
                /*val intent = Intent(context, DetailsActivity::class.java).apply {
                    putExtra("NoteId", item.id)
                }
                context.startActivity(intent)*/
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(NoteList[position])
    }

    override fun getItemCount(): Int {
        return NoteList.size
    }

}
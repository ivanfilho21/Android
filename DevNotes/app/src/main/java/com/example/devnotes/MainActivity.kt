package com.example.devnotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.devnotes.adapter.NoteAdapter
import com.example.devnotes.application.DevNotesApplication
import com.example.devnotes.entity.Note
import com.example.devnotes.viewmodel.NoteViewModel
import com.example.devnotes.viewmodel.NoteViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val newNoteActivityRequestCode = 1
    private val viewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as DevNotesApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<Note>()
        val recycler = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = NoteAdapter(list)
        recycler.adapter = adapter
        recycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        viewModel.allNotes.observe(this, { notes ->
            notes.let {
                list.clear()

                notes.forEach {
                    list.add(it)
                }

                adapter.notifyDataSetChanged()
            }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewNoteActivity::class.java)
            startActivityForResult(intent, newNoteActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newNoteActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(NewNoteActivity.NOTE_TITLE)
            val body = data?.getStringExtra(NewNoteActivity.NOTE_BODY)

            viewModel.insert(Note(0, title!!, body!!))
        } else {
            Toast.makeText(
                applicationContext,
                R.string.message_note_empty_not_saved,
                Toast.LENGTH_LONG,
            ).show()
        }
    }
}
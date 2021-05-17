package com.example.devnotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NewNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        val titleView = findViewById<EditText>(R.id.title)
        val bodyView = findViewById<EditText>(R.id.body)
        val button = findViewById<TextView>(R.id.button_save)

        titleView.requestFocus()

        button.setOnClickListener {
            val replyIntent = Intent()

            if (TextUtils.isEmpty(titleView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(NOTE_TITLE, titleView.text.toString())
                replyIntent.putExtra(NOTE_BODY, bodyView.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }

            finish()
        }
    }

    companion object {
        const val NOTE_TITLE = "note.title"
        const val NOTE_BODY = "note.body"
    }

}
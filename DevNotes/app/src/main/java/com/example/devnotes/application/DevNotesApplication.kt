package com.example.devnotes.application

import android.app.Application
import com.example.devnotes.db.NoteRoomDatabase
import com.example.devnotes.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DevNotesApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { NoteRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}
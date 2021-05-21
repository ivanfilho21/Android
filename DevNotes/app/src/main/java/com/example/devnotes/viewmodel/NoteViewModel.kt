package com.example.devnotes.viewmodel

import androidx.lifecycle.*
import com.example.devnotes.entity.Note
import com.example.devnotes.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _test = MutableLiveData(0)
    private val _allNotes = repository.allNotes.asLiveData()

    var test: LiveData<Int> = _test
    val allNotes: LiveData<List<Note>> = _allNotes
    /**
     * Abrindo uma nova corrotina para inserir o dado de forma não bloqueante
     */
    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
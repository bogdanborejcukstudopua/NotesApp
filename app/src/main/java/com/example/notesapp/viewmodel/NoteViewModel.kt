package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.notesapp.model.Note

class NoteViewModel : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private val _expandedNoteId = MutableStateFlow<Int?>(null)
    val expandedNoteId: StateFlow<Int?> = _expandedNoteId.asStateFlow()

    private var nextId = 1

    fun addNote(text: String) {
        if (text.isNotBlank()) {
            val newNote = Note(id = nextId++, text = text)
            _notes.update { currentList -> listOf(newNote) + currentList }
        }
    }

    fun removeNote(noteId: Int) {
        _notes.update { currentList -> currentList.filter { it.id != noteId } }
        if (_expandedNoteId.value == noteId) {
            _expandedNoteId.value = null
        }
    }

    fun toggleNoteExpansion(noteId: Int) {
        if (_expandedNoteId.value == noteId) {
            _expandedNoteId.value = null
        } else {
            _expandedNoteId.value = noteId
        }
    }
}
package com.example.notesapp.model

import java.sql.Timestamp

data class Note(
    val id: Int,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)
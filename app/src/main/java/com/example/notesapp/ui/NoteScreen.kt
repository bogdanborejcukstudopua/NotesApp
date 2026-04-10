package com.example.notesapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    viewModel: NoteViewModel = viewModel()
) {
    val notes by viewModel.notes.collectAsState()
    val expandedNoteId by viewModel.expandedNoteId.collectAsState()
    var inputText by remember { mutableStateOf("") }


    val screenBgColor = Color(0xFF121212)
    val topBarBgColor = Color(0xFF1E1E1E)
    val accentColor = Color(0xFF00ADB5)

    Scaffold(
        containerColor = screenBgColor, topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Нотатки: ${notes.size}", fontWeight = FontWeight.Bold, color = Color.White
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topBarBgColor
                )
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Нова нотатка") },
                    modifier = Modifier.weight(1f),
                    maxLines = 3,
                    shape = RoundedCornerShape(12.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = accentColor,
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = accentColor
                    )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = {
                        viewModel.addNote(inputText)
                        inputText = ""
                    },
                    enabled = inputText.isNotBlank(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentColor,
                        contentColor = Color.White,
                        disabledContainerColor = Color.DarkGray,
                        disabledContentColor = Color.Gray
                    )
                ) {
                    Text("+ Зберегти", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }


            LazyColumn(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(
                    items = notes, key = { note -> note.id }) { note ->
                    NoteCard(
                        note = note,
                        isExpanded = expandedNoteId == note.id,
                        onToggle = { viewModel.toggleNoteExpansion(note.id) },
                        onDelete = { viewModel.removeNote(note.id) })
                }
            }
        }
    }
}
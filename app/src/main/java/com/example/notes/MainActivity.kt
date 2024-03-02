package com.example.notes

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notes.database.NoteEntity
import com.example.notes.database.RoomInstance
import com.example.notes.screens.NoteEditScreen
import com.example.notes.screens.NoteInfoScreen
import com.example.notes.screens.NotesListScreen
import com.example.notes.ui.theme.NotesTheme
import java.util.UUID

class MainActivity : ComponentActivity() {

    private val db by lazy { RoomInstance.getInstance(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xff1D1D1D)
                ) {
                    val navController = rememberNavController()
                    val notes = remember { mutableStateListOf(*(db.getNoteDao().readAllNotes()).toTypedArray()) }

                    NavHost(
                        navController = navController,
                        startDestination = "notes_list"
                    ) {

                        composable("notes_list") {
                            NotesListScreen(
                                toNote = {
                                    navController.navigate("note_info/?noteId=${it}")
                                },
                                notes = notes,
                                onCreateNote = {
                                    navController.navigate("note_edit/?noteId=${UUID.randomUUID()}")
                                },
                                onDelete = { index ->
                                    val currentNote = notes[index]
                                    db.getNoteDao().deleteNote(currentNote)
                                    notes.remove(currentNote)
                                    Toast.makeText(applicationContext, "Заметка удалена", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }

                        composable(
                            "note_info/?noteId={noteId}",
                            arguments = listOf(navArgument("noteId") {defaultValue = "" })
                        ) {
                            val id = it.arguments?.getString("noteId")
                            if(id == "") {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                    Text(text = "Заметка не найдена", modifier = Modifier.padding(bottom = 8.dp))
                                    Button(onClick = {navController.popBackStack()}) {
                                        Text(text = "Вернуться назад")
                                    }
                                }
                            }
                            else {
                                val note = notes.find{ noteEntity -> noteEntity.id.toString() == id}
                                if(note != null) {
                                    NoteInfoScreen(
                                        note = note,
                                        onBack = {
                                            navController.popBackStack()
                                        },
                                        onEdit = {
                                            navController.navigate("note_edit/?noteId=${id}")
                                        },
                                        onDelete = {
                                            db.getNoteDao().deleteNote(note)
                                            notes.remove(note)
                                            navController.popBackStack()
                                            Toast.makeText(applicationContext, "Заметка удалена", Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                }
                            }
                        }

                        composable(
                            "note_edit/?noteId={noteId}",
                            arguments = listOf(navArgument("noteId") {defaultValue = ""})
                        ) {
                            var id = it.arguments?.getString("noteId")
                            if(id == null || id == "") id = UUID.randomUUID().toString()
                            val newNote = NoteEntity(id = UUID.fromString(id), "Заголовок", "")
                            val note = notes.find{ noteEntity -> noteEntity.id.toString() == id}
                            NoteEditScreen(
                                note = note ?: newNote,
                                onBack = {
                                    navController.popBackStack()
                                },
                                onSave = { noteEntity ->
                                    Log.e("notes", notes.map{ it.id }.toString())
                                    Log.e("notes", noteEntity.id.toString() )
                                    if(note != null && noteEntity.id == note.id) {
                                        db.getNoteDao().updateNote(noteEntity)
                                        notes[notes.indexOf(note)] = noteEntity
                                    }
                                    else if(noteEntity.id == newNote.id) {
                                        db.getNoteDao().createNote(noteEntity)
                                        notes.add(noteEntity)
                                    }
                                    Toast.makeText(applicationContext, "Изменения сохранены", Toast.LENGTH_SHORT).show()
                                },
                                onDelete = {
                                    if(note != null) {
                                        db.getNoteDao().deleteNote(note)
                                        notes.remove(note)
                                        navController.popBackStack("notes_list", false)
                                        Toast.makeText(applicationContext, "Заметка удалена", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

package com.example.notes.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.UUID

@Dao
interface NoteDao {

    @Insert
    fun createNote(note: NoteEntity)

    @Insert
    fun createNotes(notes: List<NoteEntity>)

    @Query("SELECT * FROM note_table WHERE id=(:id)")
    fun readNoteById(id: UUID): NoteEntity?

    @Query("SELECT * FROM note_table")
    fun readAllNotes(): List<NoteEntity>

    @Update
    fun updateNote(note: NoteEntity)

    @Delete
    fun deleteNote(note: NoteEntity)

}
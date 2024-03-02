package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class RoomInstance: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: RoomInstance? = null

        fun getInstance(context: Context): RoomInstance {
            return instance ?: synchronized(this) {
                val inst = Room.databaseBuilder(
                    context,
                    RoomInstance::class.java,
                    "note-database"
                ).allowMainThreadQueries().build()

//                inst.getNoteDao().createNotes(mockNotes.map { note -> NoteEntity(note.id, note.title, note.content) })

                instance = inst
                inst
            }
        }
    }
}
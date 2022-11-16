package com.bignerdranch.android.notesapp.dao

import androidx.room.*
import com.bignerdranch.android.notesapp.entities.Notes

@Dao
interface NoteDao {
    @get:Query("SELECT * FROM notes ORDER BY id DESC")
    val allNotes: List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertNotes(notes: Notes)

    @Delete
    suspend fun deleteNote(notes: Notes)
}
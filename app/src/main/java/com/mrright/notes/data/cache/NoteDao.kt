package com.mrright.notes.data.cache

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity): Long

    @Update
    suspend fun update(note: NoteEntity): Int

    @Delete
    suspend fun delete(note: NoteEntity): Int

    @Query("SELECT * FROM note_table WHERE id = :id")
    suspend fun getNote(id: Int): NoteEntity

    @Query("SELECT * FROM note_table ORDER BY updated DESC")
    fun getNotes(): LiveData<List<NoteEntity>>

}
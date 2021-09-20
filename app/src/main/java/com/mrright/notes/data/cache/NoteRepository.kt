package com.mrright.notes.data.cache

import androidx.lifecycle.LiveData
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
) {

    suspend fun insert(noteEntity: NoteEntity): Long {
        return noteDao.insert(noteEntity)
    }

    suspend fun update(noteEntity: NoteEntity): Int {
        return noteDao.update(noteEntity)
    }

    suspend fun delete(noteEntity: NoteEntity): Int {
        return noteDao.delete(noteEntity)
    }

    suspend fun getNote(id: Int): NoteEntity {
        return noteDao.getNote(id)
    }

    fun getNotes(): LiveData<List<NoteEntity>> {
        return noteDao.getNotes()
    }


}
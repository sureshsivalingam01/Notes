package com.mrright.notes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.mrright.notes.data.cache.NoteEntity
import com.mrright.notes.data.cache.NoteRepository
import com.mrright.notes.utils.Note
import com.mrright.notes.utils.Note.NEW
import com.mrright.notes.utils.Note.OLD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
) : ViewModel() {

    private val _notes: LiveData<List<NoteEntity>> = noteRepository.getNotes()
    val notes: LiveData<List<NoteEntity>> get() = _notes

    private val _noteData: MutableLiveData<NoteEntity?> = MutableLiveData<NoteEntity?>(null)
    val noteData: LiveData<NoteEntity?> get() = _noteData

    private val _btnEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val btnEnabled: StateFlow<Boolean> get() = _btnEnabled


    private suspend fun insert(note: String) {
        val job = viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insert(
                NoteEntity(
                    note = note,
                    created = Timestamp.now().seconds,
                    updated = Timestamp.now().seconds,
                )
            )
        }
        job.join()
        nullNoteData()
    }


    private suspend fun update(note: String) {
        val job = viewModelScope.launch(Dispatchers.IO) {
            noteRepository.update(
                NoteEntity(
                    id = noteData.value?.id!!,
                    note = note,
                    created = noteData.value?.created,
                    updated = Timestamp.now().seconds,
                )
            )
        }
        job.join()
        nullNoteData()
    }


    fun getNote(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            _noteData.value = noteRepository.getNote(id)
        }
    }


    suspend fun delete() {
        val job = viewModelScope.launch(Dispatchers.IO) {
            noteRepository.delete(noteData.value!!)
        }
        job.join()
        nullNoteData()
    }


    fun setData(note: String, noteMode: Note) {
        viewModelScope.launch(Dispatchers.Main) {
            when (noteMode) {
                NEW -> insert(note)
                OLD -> update(note)
            }
        }

    }


    fun onValueChange(text: String?) {
        _btnEnabled.value = !text.isNullOrBlank()
    }


    fun nullNoteData() {
        _noteData.value = null
        _btnEnabled.value = false
    }

}
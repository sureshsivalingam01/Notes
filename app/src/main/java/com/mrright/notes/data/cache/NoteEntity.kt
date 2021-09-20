package com.mrright.notes.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "note_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val note: String = "",
    val created: Long? = null,
    val updated: Long? = null,
) {

    fun getMonthDate(): String =
        SimpleDateFormat("MMM dd", Locale.getDefault()).format(updated)
}

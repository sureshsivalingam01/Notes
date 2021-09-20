package com.mrright.notes.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrright.notes.data.cache.NoteEntity
import com.mrright.notes.databinding.ItemNoteBinding

class NotesAdapter(
    private val onNoteClick: (NoteEntity) -> Unit,
) : ListAdapter<NoteEntity, NotesAdapter.NoteViewHolder>(NoteUtil()) {


    inner class NoteViewHolder(val bind: ItemNoteBinding) : RecyclerView.ViewHolder(bind.root) {
        init {
            bind.root.setOnClickListener {
                onNoteClick(currentList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = currentList[position]

        with(holder.bind) {
            txtTime.text = note.getMonthDate()
            txtNote.text = note.note
        }

    }

    override fun getItemCount() = currentList.size

    class NoteUtil : DiffUtil.ItemCallback<NoteEntity>() {

        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
        }

    }
}



package com.mrright.notes.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrright.notes.R
import com.mrright.notes.databinding.ActivityMainBinding
import com.mrright.notes.utils.DIALOG_TAG
import com.mrright.notes.utils.Note
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding

    private lateinit var notesAdapter: NotesAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        initViews()
        observerNotes()

    }

    private fun observerNotes() {
        viewModel.notes.observe(this) {
            notesAdapter.submitList(it)
        }
    }

    private fun initViews() {
        notesAdapter = NotesAdapter {
            val noteDialog = NoteDialogFragment.newInstance(Note.OLD, it.id)
            noteDialog.show(supportFragmentManager, DIALOG_TAG)
        }
        bind.rvNotes.also {
            it.adapter = notesAdapter
            it.layoutManager = LinearLayoutManager(it.context)
            val itemDecoration = DividerItemDecoration(it.context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(ContextCompat.getDrawable(it.context, R.drawable.layer)!!)
            it.addItemDecoration(itemDecoration)
        }
        bind.fabNew.setOnClickListener {
            val noteDialog = NoteDialogFragment.newInstance(Note.NEW)
            noteDialog.show(supportFragmentManager, DIALOG_TAG)
        }
    }

}
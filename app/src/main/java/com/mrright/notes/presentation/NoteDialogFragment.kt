package com.mrright.notes.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.mrright.notes.R
import com.mrright.notes.databinding.DialogNoteBinding
import com.mrright.notes.utils.Note
import com.mrright.notes.utils.inVisible
import com.mrright.notes.utils.setStringRes
import com.mrright.notes.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteDialogFragment : DialogFragment() {

    private var _bind: DialogNoteBinding? = null
    private val bind get() = _bind!!

    private val viewModel: MainViewModel by activityViewModels()

    private var noteId: Int? = null
    private var note: Note = Note.NEW


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = DialogNoteBinding.inflate(inflater, container, false)
        bind.root.inVisible()
        return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getArgs()
        clicks()
        observerNote()
        collectBtnState()
    }

    private fun getArgs() {
        noteId = arguments?.getInt(NOTE_ID)
        note = Note.valueOf(arguments?.getString(NOTE_MODE) ?: Note.NEW.value)
        noteId?.let {
            viewModel.getNote(it)
        }
        when (note) {
            Note.NEW -> {
                bind.txtTitle.setStringRes(R.string.new_note)
                bind.btnDelete.inVisible()
            }
            Note.OLD -> {
                bind.txtTitle.setStringRes(R.string.note)
            }
        }


    }

    private fun clicks() {
        with(bind){
            btnCancel.setOnClickListener {
                bind.etNote.setText("")
                viewModel.nullNoteData()
                this@NoteDialogFragment.dismiss()
            }

            btnSave.setOnClickListener {
                viewModel.setData(bind.etNote.text.toString(), note)
                bind.etNote.setText("")
                this@NoteDialogFragment.dismiss()
            }
            btnDelete.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.delete()
                }
                bind.etNote.setText("")
                this@NoteDialogFragment.dismiss()
            }

            etNote.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(editable: Editable?) {
                    viewModel.onValueChange(editable.toString())
                }

            })
        }
    }

    private fun observerNote() {
        viewModel.noteData.observe(viewLifecycleOwner) { noteEntity ->
            noteEntity?.let {
                bind.etNote.setText(it.note)
                if (!bind.root.isVisible) {
                    bind.root.visible()
                }

            }
        }
    }

    private fun collectBtnState() {
        lifecycleScope.launchWhenCreated {
            viewModel.btnEnabled.collect {
                bind.btnSave.isEnabled = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    companion object {

        private const val NOTE_MODE = "NOTE_MODE"
        private const val NOTE_ID = "NOTE_ID"

        fun newInstance(
            note: Note,
            noteId: Int? = null,
        ) = NoteDialogFragment().apply {
            arguments = Bundle().apply {
                putString(NOTE_MODE, note.value)
                putInt(NOTE_ID, noteId ?: 0)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
    }

}




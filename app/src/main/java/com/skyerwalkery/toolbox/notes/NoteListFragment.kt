package com.skyerwalkery.toolbox.notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skyerwalkery.toolbox.databinding.FragmentNoteListBinding

class NoteListFragment: Fragment() {
    companion object {
        private val TAG: String = NoteListFragment::class.java.simpleName
    }

    private val noteListViewModel: NoteListViewModel by viewModels()

    private var _binding: FragmentNoteListBinding? = null
    private val binding: FragmentNoteListBinding
        get() = checkNotNull(_binding){
            Log.d(TAG, "_binding is null")
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteListBinding.inflate(layoutInflater, container, false)

        binding.noteListRecyclerView.layoutManager = LinearLayoutManager(context)

        val notes: List<Note> = noteListViewModel.notes
        val adapter: NoteListAdapter = NoteListAdapter(notes)
        binding.noteListRecyclerView.adapter = adapter

        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
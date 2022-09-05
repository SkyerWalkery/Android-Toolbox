package com.skyerwalkery.toolbox.notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.skyerwalkery.toolbox.databinding.FragmentNoteDetailBinding


class NoteDetailFragment: Fragment() {
    companion object {
        private val TAG: String = NoteDetailFragment::class.java.simpleName
    }

    private val noteDetailViewModel: NoteDetailViewModel by viewModels()
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding: FragmentNoteDetailBinding
        get() = checkNotNull(_binding){
            Log.d(TAG, "_binding is null")
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteDetailBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            noteDetailTitle.doOnTextChanged{ text, _, _, _ ->
                noteDetailViewModel.note.title = text.toString()
            }
            // TODO: Modify Note Date
            /*
            noteDetailDate.doOnTextChanged{ text, _, _, _ ->
                noteDetailViewModel.note.date = Date(text)
            }
            */
            noteDetailContent.doOnTextChanged { text, _, _, _ ->
                noteDetailViewModel.note.content = text.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
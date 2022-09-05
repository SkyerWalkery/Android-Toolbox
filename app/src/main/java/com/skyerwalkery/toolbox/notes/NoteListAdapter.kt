package com.skyerwalkery.toolbox.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skyerwalkery.toolbox.databinding.ListItemNoteBinding

class NoteListAdapter(
    private val notes: List<Note>
) : RecyclerView.Adapter<NoteHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNoteBinding.inflate(inflater, parent, false)
        return NoteHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = notes[position]
        holder.apply {
            binding.listItemNoteTitle.text = note.title
            binding.listItemNoteDate.text = note.date.toString()
            binding.listItemNoteContent.text = note.content
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}
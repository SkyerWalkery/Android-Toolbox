package com.skyerwalkery.toolbox.notes

import androidx.lifecycle.ViewModel
import java.util.*

class NoteListViewModel: ViewModel() {
    val notes: MutableList<Note> = mutableListOf()

    init {
        repeat(50){
            val note = Note(
                id= UUID.randomUUID(),
                date = Date(),
                title = "test",
                content = "哈哈哈，来，我试试汉语。你好世界。\n换一行。"
            )
            notes.add(note)
        }
    }
}
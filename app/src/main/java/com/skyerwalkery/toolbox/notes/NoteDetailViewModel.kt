package com.skyerwalkery.toolbox.notes

import androidx.lifecycle.ViewModel
import java.util.*

class NoteDetailViewModel: ViewModel() {
    val note: Note = Note(
        id = UUID.randomUUID(),
        date = Date()
    )

}
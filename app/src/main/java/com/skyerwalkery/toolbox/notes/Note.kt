package com.skyerwalkery.toolbox.notes

import java.util.*

data class Note (
    val id: UUID,
    var date: Date,
    var title: String = "",
    var content: String = "",
)
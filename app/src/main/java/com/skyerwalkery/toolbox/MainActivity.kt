package com.skyerwalkery.toolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.skyerwalkery.toolbox.notes.NotesActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getLocationOnClick(view: View){
        startActivity(Intent(this, GetLocationActivity::class.java))
    }

    fun notesOnClick(view: View){
        startActivity(Intent(this, NotesActivity::class.java))
    }
}
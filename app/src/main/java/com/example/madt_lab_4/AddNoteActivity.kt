package com.example.madt_lab_4

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddNoteActivity : AppCompatActivity() {

    private lateinit var noteName: EditText
    private lateinit var noteContent: EditText
    private lateinit var addButton: Button

    private fun saveNote() {
        // Your code to save the note

        // Set a flag indicating that a new note has been added
        val sharedPreferences = getSharedPreferences("YourNotes", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isNewNoteAdded", true).apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_note_activity)

        noteName= findViewById(R.id.noteName)
        noteContent = findViewById(R.id.noteContent)
        addButton = findViewById(R.id.saveNote)

        addButton.setOnClickListener {

            val noteName = noteName.text.toString()
            val noteContent = noteContent.text.toString()

            // Create a Note object with the noteName and noteContent
            val note = Note(noteName, noteContent)


            saveNoteToSharedPreferences(note)
            saveNote()

            finish()
        }

    }
    private fun saveNoteToSharedPreferences(note: Note) {
        val sharedPreferences = getSharedPreferences("YourNotes", Context.MODE_PRIVATE)
        val gson = Gson()

        // Retrieve the existing list of notes from SharedPreferences (if any)
        val existingNotesJson = sharedPreferences.getString("notes", null)
        val existingNotes = if (existingNotesJson != null) {
            gson.fromJson(existingNotesJson, object : TypeToken<List<Note>>() {}.type)
        } else {
            ArrayList<Note>()
        }

        // Add the new note to the existing list of notes
        existingNotes.add(note)

        // Convert the updated list of notes to JSON and save it back to SharedPreferences
        val updatedNotesJson = gson.toJson(existingNotes)
        sharedPreferences.edit().putString("notes", updatedNotesJson).apply()


    }

}
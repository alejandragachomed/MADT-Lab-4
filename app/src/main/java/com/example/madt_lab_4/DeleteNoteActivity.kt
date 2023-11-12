package com.example.madt_lab_4

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.widget.Toast


class DeleteNoteActivity : AppCompatActivity() {

    private lateinit var deleteButton: Button
    private var selectedNotePos: Int = ListView.INVALID_POSITION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_note_activity)

        deleteButton = findViewById(R.id.deleteButton)
        val listView = findViewById<ListView>(R.id.listView)

        // Retrieve the list of notes from SharedPreferences
        val sharedPreferences = getSharedPreferences("YourNotes", Context.MODE_PRIVATE)
        val gson = Gson()
        val notesJson = sharedPreferences.getString("notes", null)

        val notes = if (notesJson != null) {
            gson.fromJson(notesJson, object : TypeToken<List<Note>>() {}.type)
        } else {
            ArrayList<Note>()
        }

        // Use an ArrayAdapter to display the notes in a ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notes)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            // Update the selected note position
            selectedNotePos = position
        }

        deleteButton.setOnClickListener {
            if (selectedNotePos != ListView.INVALID_POSITION) {
                // Call the deleteNote function with the selected position
                deleteNote(selectedNotePos)
                // Optionally, you can return to the main activity after deleting the note
                finish()
            } else {
                Toast.makeText(applicationContext, "Please select a note to delete", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun deleteNote(position: Int) {
        val sharedPreferences = getSharedPreferences("YourNotes", Context.MODE_PRIVATE)
        val gson = Gson()

        // Retrieve the existing list of notes from SharedPreferences
        val existingNotesJson = sharedPreferences.getString("notes", null)
        val existingNotes = if (existingNotesJson != null) {
            gson.fromJson(existingNotesJson, object : TypeToken<List<Note>>() {}.type)
        } else {
            ArrayList<Note>()
        }

        // Check if the position is valid
        if (position in 0 until existingNotes.size) {
            // Remove the note at the specified position
            existingNotes.removeAt(position)

            // Convert the updated list of notes to JSON and save it back to SharedPreferences
            val updatedNotesJson = gson.toJson(existingNotes)
            sharedPreferences.edit().putString("notes", updatedNotesJson).apply()

            // Optionally, you can return to the main activity after deleting the note
            finish()
        }
    }
}








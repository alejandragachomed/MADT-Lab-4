package com.example.madt_lab_4

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.content.Context
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.widget.Toast



class MainActivity : AppCompatActivity() {


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        Log.d("Menu", "Menu created")
        return true
    }

    override fun onResume() {
        super.onResume()
        setupListView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_note -> {

                startActivity(Intent(this, AddNoteActivity::class.java))

                return true
            }
            R.id.action_delete_note -> {

                val intent = Intent(this, DeleteNoteActivity::class.java)
                startActivity(intent)

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //  val menuView = findViewById<ListView>(R.id.listViewM)
        //  registerForContextMenu(menuView)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        setupListView()

    }

    private fun setupListView() {
        val listView = findViewById<ListView>(R.id.listView)
        val sharedPreferences = getSharedPreferences("YourNotes", Context.MODE_PRIVATE)
        val gson = Gson()
        val notesJson = sharedPreferences.getString("notes", null)

        val notes = if (notesJson != null) {
            gson.fromJson(notesJson, object : TypeToken<List<Note>>() {}.type)
        } else {
            ArrayList<Note>()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notes)
        listView.adapter = adapter
    }
}
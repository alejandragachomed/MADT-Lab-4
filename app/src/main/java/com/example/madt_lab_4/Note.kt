package com.example.madt_lab_4


data class Note(val noteName: String, val noteContent: String) {
    override fun toString(): String {
        return "$noteName: $noteContent"
    }
}
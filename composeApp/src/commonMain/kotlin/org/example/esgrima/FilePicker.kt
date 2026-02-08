package org.example.esgrima

expect class FilePicker() {
    fun saveFile(content: String, defaultName: String)
    fun readFile(): String?
}

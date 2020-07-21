package com.bazooka.overnote.service

import com.bazooka.overnote.exception.ResourceNotFoundException
import com.bazooka.overnote.model.Note
import com.bazooka.overnote.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.*

@Service
class NoteService {

    @Autowired
    private lateinit var noteRepository: NoteRepository

    fun findAll(): Iterable<Note> =
            noteRepository.findAll()

    fun saveNote(note: Note): Note =
            noteRepository.save(note)

    fun findNoteById(noteId: Int): Note {
        val result: Optional<Note> = noteRepository.findById(noteId)

        if (result.isEmpty)
            throw ResourceNotFoundException("Note with ID: $noteId was not found")

        return result.get()
    }

    fun updateNoteById(noteId: Int, newNote: Note): Note {
        val oldNote = findNoteById(noteId)
        val updatedNote = oldNote.copy(title = newNote.title, body = newNote.body)
        return saveNote(updatedNote)
    }

    fun deleteNoteById(noteId: Int) {
        val note = findNoteById(noteId)
        return noteRepository.delete(note)
    }
}
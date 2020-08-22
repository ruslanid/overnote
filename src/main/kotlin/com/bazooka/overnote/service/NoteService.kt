package com.bazooka.overnote.service

import com.bazooka.overnote.exception.ResourceNotFoundException
import com.bazooka.overnote.model.Note
import com.bazooka.overnote.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class NoteService {

    @Autowired
    private lateinit var noteRepository: NoteRepository

    fun saveNote(note: Note): Note {
        return noteRepository.save(note)
    }

    fun findAll(): Iterable<Note> {
        return noteRepository.findAll()
    }


    fun findNoteById(id: Int): Note {
        val result: Optional<Note> = noteRepository.findById(id)

        if (result.isEmpty)
            throw ResourceNotFoundException("Note with ID: $id was not found")

        return result.get()
    }

    fun updateNoteById(newNote: Note, id: Int): Note {
        val oldNote = findNoteById(id)
        val updatedNote = oldNote.copy(title = newNote.title, description = newNote.description)

        return noteRepository.save(updatedNote)
    }

    fun deleteNoteById(id: Int) {
        val note = findNoteById(id)
        return noteRepository.delete(note)
    }
}

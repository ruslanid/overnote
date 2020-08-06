package com.bazooka.overnote.service

import com.bazooka.overnote.exception.ResourceNotFoundException
import com.bazooka.overnote.model.Category
import com.bazooka.overnote.model.Note
import com.bazooka.overnote.repository.CategoryRepository
import com.bazooka.overnote.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class NoteService {

    @Autowired
    private lateinit var noteRepository: NoteRepository

    @Autowired lateinit var categoryRepository: CategoryRepository

    fun saveNote(note: Note): Note {
        return noteRepository.save(note)
    }

    fun saveNoteCategory(note: Note, categoryId: Int): Note {
        note.category = findCategoryById(categoryId)

        return noteRepository.save(note)
    }

    fun findAll(): Iterable<Note> =
            noteRepository.findAll()

    fun findNoteById(noteId: Int): Note {
        val result: Optional<Note> = noteRepository.findById(noteId)

        if (result.isEmpty)
            throw ResourceNotFoundException("Note with ID: $noteId was not found")

        return result.get()
    }

    fun findNotesByCategoryId(categoryId: Int): Iterable<Note> =
        noteRepository.findByCategoryId(categoryId)


    fun updateNoteCategory(newNote: Note, noteId: Int, categoryId: Int): Note {
        val oldNote = findNoteById(noteId)
        val updatedNote = oldNote.copy(title = newNote.title, body = newNote.body)

        updatedNote.category = findCategoryById(categoryId)

        return noteRepository.save(updatedNote)
    }

    fun updateNoteById(noteId: Int, newNote: Note): Note {
        val oldNote = findNoteById(noteId)
        val updatedNote = oldNote.copy(title = newNote.title, body = newNote.body)

        return noteRepository.save(updatedNote)
    }

    fun deleteNoteById(noteId: Int) {
        val note = findNoteById(noteId)
        return noteRepository.delete(note)
    }

    private fun findCategoryById(categoryId: Int): Category? {
        val result = categoryRepository.findById(categoryId)

        if (result.isEmpty) return null

        return result.get()
    }
}
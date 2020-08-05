package com.bazooka.overnote.controller

import com.bazooka.overnote.model.Note
import com.bazooka.overnote.service.EntityValidationService
import com.bazooka.overnote.service.NoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class NoteController {

    @Autowired
    private lateinit var noteService: NoteService

    @Autowired
    private lateinit var entityValidationService: EntityValidationService

    @GetMapping("/notes")
    fun getAllNotes(): Iterable<Note> =
            noteService.findAll()

    @PostMapping("/categories/{categoryId}/notes")
    fun createNewNote(@PathVariable categoryId: Int,
                        @Valid @RequestBody note: Note, errors: Errors): ResponseEntity<*> {

        if (errors.hasErrors()) {
            return entityValidationService.validateFields(errors)
        }

        val newNote = noteService.saveNote(categoryId, note)
        return ResponseEntity.ok(newNote)
    }

    @GetMapping("/notes/{id}")
    fun getNoteById(@PathVariable(value = "id") noteId: Int): ResponseEntity<Note> {
        val note: Note = noteService.findNoteById(noteId)
        return ResponseEntity.ok().body(note)
    }

    @GetMapping("/categories/{categoryId}/notes")
    fun getNotesByCategoryId(@PathVariable categoryId: Int): Iterable<Note> =
            noteService.findNotesByCategoryId(categoryId)

    @PutMapping("categories/{categoryId}/notes/{noteId}")
    fun updateNoteById(@PathVariable categoryId: Int, @PathVariable noteId: Int,
                        @Valid @RequestBody newNote: Note, errors: Errors): ResponseEntity<*> {

        if (errors.hasErrors()) {
            return entityValidationService.validateFields(errors)
        }

        val updatedNote: Note = noteService.updateNoteById(categoryId, noteId, newNote)
        return ResponseEntity.ok().body(updatedNote)
    }

    @DeleteMapping("/notes/{id}")
    fun deleteNoteById(@PathVariable(value = "id") noteId: Int): ResponseEntity<Void> {
        noteService.deleteNoteById(noteId)
        return ResponseEntity.ok().build()
    }
}
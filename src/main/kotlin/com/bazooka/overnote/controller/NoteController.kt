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
class NoteController() {

    @Autowired
    private lateinit var noteService: NoteService

    @Autowired
    private lateinit var entityValidationService: EntityValidationService

    @GetMapping("/notes")
    fun getAllNotes(): Iterable<Note> =
            noteService.findAll()

    @PostMapping("/notes")
    fun createNewNote(@Valid @RequestBody note: Note, errors: Errors): ResponseEntity<*> {
        if (errors.hasErrors()) {
            return entityValidationService.validateFields(errors)
        }

        val note = noteService.saveNote(note);
        return ResponseEntity.ok(note)
    }

    @GetMapping("/notes/{id}")
    fun getNoteById(@PathVariable(value = "id") noteId: Int): ResponseEntity<Note> {
        val note: Note = noteService.findNoteById(noteId)
        return ResponseEntity.ok().body(note)
    }

    @PutMapping("/notes/{id}")
    fun updateNoteById(@PathVariable(value = "id") noteId: Int,
                          @Valid @RequestBody newNote: Note, errors: Errors): ResponseEntity<*> {

        if (errors.hasErrors()) {
            return entityValidationService.validateFields(errors)
        }

        val updatedNote: Note = noteService.updateNoteById(noteId, newNote)
        return ResponseEntity.ok().body(updatedNote)
    }

    @DeleteMapping("/notes/{id}")
    fun deleteNoteById(@PathVariable(value = "id") noteId: Int): ResponseEntity<Void> {
        noteService.deleteNoteById(noteId)
        return ResponseEntity.ok().build()
    }
}
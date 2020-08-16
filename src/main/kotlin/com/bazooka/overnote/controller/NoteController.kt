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

    @PostMapping("/notes")
    fun createNote(@Valid @RequestBody note: Note, errors: Errors): ResponseEntity<*> {
        if (errors.hasErrors()) {
            return entityValidationService.validateFields(errors)
        }

        val newNote = noteService.saveNote(note)
        return ResponseEntity.ok(newNote)
    }

    @GetMapping("/notes")
    fun getNotes(@RequestParam q: String): Iterable<Note> =
        noteService.findAll(q)

    @GetMapping("/notes/{id}")
    fun getNote(@PathVariable id: Int): ResponseEntity<Note> {
        val note: Note = noteService.findNoteById(id)
        return ResponseEntity.ok().body(note)
    }

    @PutMapping("/notes/{id}")
    fun updateNoteById(@PathVariable id: Int,
                       @Valid @RequestBody note: Note, errors: Errors): ResponseEntity<*> {

        if (errors.hasErrors()) {
            return entityValidationService.validateFields(errors)
        }

        val updatedNote: Note = noteService.updateNoteById(note, id)
        return ResponseEntity.ok().body(updatedNote)
    }

    @DeleteMapping("notes/{id}")
    fun deleteNote(@PathVariable id: Int): ResponseEntity<Void> {
        noteService.deleteNoteById(id)
        return ResponseEntity.ok().build()
    }
}

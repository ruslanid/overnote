package com.bazooka.overnote.controller
import com.bazooka.overnote.model.Note
import com.bazooka.overnote.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ArticleController() {

    @Autowired
    private lateinit var noteRepository: NoteRepository

    @GetMapping("/notes")
    fun getAllNotes(): Iterable<Note> =
            noteRepository.findAll()


    @PostMapping("/notes")
    fun createNewNote(@Valid @RequestBody note: Note): Note =
            noteRepository.save(note)


    @GetMapping("/notes/{id}")
    fun getNoteById(@PathVariable(value = "id") noteId: Int): ResponseEntity<Note> {
        return noteRepository.findById(noteId).map { note ->
            ResponseEntity.ok(note)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/notes/{id}")
    fun updateNoteById(@PathVariable(value = "id") noteId: Int,
                          @Valid @RequestBody newNote: Note): ResponseEntity<Note> {

        return noteRepository.findById(noteId).map { existingNote ->
            val updatedArticle: Note = existingNote
                    .copy(title = newNote.title, body = newNote.body)
            ResponseEntity.ok().body(noteRepository.save(updatedArticle))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/notes/{id}")
    fun deleteNoteById(@PathVariable(value = "id") noteId: Int): ResponseEntity<Void> {

        return noteRepository.findById(noteId).map { note  ->
            noteRepository.delete(note)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}
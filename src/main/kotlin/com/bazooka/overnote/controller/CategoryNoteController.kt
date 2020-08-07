package com.bazooka.overnote.controller

import com.bazooka.overnote.model.Note
import com.bazooka.overnote.service.CategoryNoteService
import com.bazooka.overnote.service.EntityValidationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class CategoryNoteController {

  @Autowired
  private lateinit var categoryNoteService: CategoryNoteService

  @Autowired
  private lateinit var entityValidationService: EntityValidationService

  @PostMapping("/categories/{categoryId}/notes")
  fun createNote(@PathVariable categoryId: Int,
                 @Valid @RequestBody note: Note, errors: Errors): ResponseEntity<*> {

    if (errors.hasErrors()) {
      return entityValidationService.validateFields(errors)
    }

    val newNote = categoryNoteService.saveCategoryNote(categoryId, note)
    return ResponseEntity.ok(newNote)
  }

  @GetMapping("/categories/{categoryId}/notes")
  fun getNotesByCategoryId(@PathVariable categoryId: Int): Iterable<Note> =
      categoryNoteService.findNotesByCategoryId(categoryId)
}
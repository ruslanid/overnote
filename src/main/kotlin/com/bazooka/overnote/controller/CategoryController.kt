package com.bazooka.overnote.controller

import com.bazooka.overnote.model.Category
import com.bazooka.overnote.model.Note
import com.bazooka.overnote.service.CategoryService
import com.bazooka.overnote.service.EntityValidationService
import com.bazooka.overnote.service.NoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class CategoryController {

  @Autowired
  private lateinit var noteService: NoteService

  @Autowired
  private lateinit var categoryService: CategoryService

  @Autowired
  private lateinit var entityValidationService: EntityValidationService

  @GetMapping("/categories")
  fun getAllCategories(): Iterable<Category> =
          categoryService.findAll()

  @PostMapping("/categories")
  fun createNewCategory(@Valid @RequestBody category: Category, errors: Errors): ResponseEntity<*> {
    if (errors.hasErrors()) {
      return entityValidationService.validateFields(errors)
    }

    val newCategory = categoryService.saveCategory(category);
    return ResponseEntity.ok(newCategory)
  }

  @GetMapping("/categories/{id}")
  fun getCategoryById(@PathVariable(value = "id") categoryId: Int): ResponseEntity<Category> {
    val category: Category = categoryService.findCategoryById(categoryId)
    return ResponseEntity.ok().body(category)
  }

  @PutMapping("/categories/{id}")
  fun updateCategoryById(@PathVariable(value = "id") categoryId: Int,
                     @Valid @RequestBody newCategory: Category, errors: Errors): ResponseEntity<*> {

    if (errors.hasErrors()) {
      return entityValidationService.validateFields(errors)
    }

    val updatedCategory: Category = categoryService.updateCategoryById(categoryId, newCategory)
    return ResponseEntity.ok().body(updatedCategory)
  }

  @DeleteMapping("/categories/{id}")
  fun deleteCategoryById(@PathVariable(value = "id") categoryId: Int): ResponseEntity<Void> {
    categoryService.deleteCategoryById(categoryId)
    return ResponseEntity.ok().build()
  }

  @PostMapping("/categories/{categoryId}/notes")
  fun createNote(@PathVariable categoryId: Int,
                      @Valid @RequestBody note: Note, errors: Errors): ResponseEntity<*> {

    if (errors.hasErrors()) {
      return entityValidationService.validateFields(errors)
    }

    val newNote = noteService.saveNoteCategory(note, categoryId)
    return ResponseEntity.ok(newNote)
  }

   @GetMapping("/categories/{categoryId}/notes")
    fun getNotesByCategoryId(@PathVariable categoryId: Int): Iterable<Note> =
          noteService.findNotesByCategoryId(categoryId)

  @PutMapping("categories/{categoryId}/notes/{noteId}")
  fun updateNoteById(@PathVariable categoryId: Int, @PathVariable noteId: Int,
                      @Valid @RequestBody note: Note, errors: Errors): ResponseEntity<*> {

    if (errors.hasErrors()) {
      return entityValidationService.validateFields(errors)
    }

    val updatedNote: Note = noteService.updateNoteCategory(note, noteId, categoryId)
    return ResponseEntity.ok().body(updatedNote)
  }
}

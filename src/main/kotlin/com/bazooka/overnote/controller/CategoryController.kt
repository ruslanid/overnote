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
  private lateinit var categoryService: CategoryService

  @Autowired
  private lateinit var entityValidationService: EntityValidationService

  @PostMapping("/categories")
  fun createNewCategory(@Valid @RequestBody category: Category, errors: Errors): ResponseEntity<*> {
    if (errors.hasErrors()) {
      return entityValidationService.validateFields(errors)
    }

    val newCategory = categoryService.saveCategory(category);
    return ResponseEntity.ok(newCategory)
  }

  @GetMapping("/categories")
  fun getAllCategories(): Iterable<Category> =
          categoryService.findAll()

  @GetMapping("/categories/{id}")
  fun getCategoryById(@PathVariable id: Int): ResponseEntity<Category> {
    val category: Category = categoryService.findCategoryById(id)
    return ResponseEntity.ok().body(category)
  }

  @PutMapping("/categories/{id}")
  fun updateCategoryById(@PathVariable id: Int,
                     @Valid @RequestBody newCategory: Category, errors: Errors): ResponseEntity<*> {

    if (errors.hasErrors()) {
      return entityValidationService.validateFields(errors)
    }

    val updatedCategory: Category = categoryService.updateCategoryById(id, newCategory)
    return ResponseEntity.ok().body(updatedCategory)
  }

  @DeleteMapping("/categories/{id}")
  fun deleteCategoryById(@PathVariable id: Int): ResponseEntity<Void> {
    categoryService.deleteCategoryById(id)
    return ResponseEntity.ok().build()
  }
}

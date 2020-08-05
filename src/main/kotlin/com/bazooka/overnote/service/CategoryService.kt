package com.bazooka.overnote.service

import com.bazooka.overnote.exception.ResourceNotFoundException
import com.bazooka.overnote.model.Category
import com.bazooka.overnote.repository.CategoryRepository
import com.bazooka.overnote.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryService {

  @Autowired
  private lateinit var categoryRepository: CategoryRepository

  fun findAll(): Iterable<Category> =
          categoryRepository.findAll()

  fun saveCategory(category: Category): Category =
          categoryRepository.save(category)

  fun findCategoryById(categoryId: Int): Category {
    val result: Optional<Category> = categoryRepository.findById(categoryId)

    if (result.isEmpty)
        throw ResourceNotFoundException("Category with ID: $categoryId was not found")

    return result.get()
  }

  fun updateCategoryById(categoryId: Int, newCategory: Category): Category {
    val oldCategory = findCategoryById(categoryId)
    val updatedCategory = oldCategory.copy(name = newCategory.name)
    return saveCategory(updatedCategory)
  }

  fun deleteCategoryById(categoryId: Int) {
    val category = findCategoryById(categoryId)
    return categoryRepository.delete(category)
  }
}
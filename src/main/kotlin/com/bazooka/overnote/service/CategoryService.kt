package com.bazooka.overnote.service

import com.bazooka.overnote.exception.ResourceNotFoundException
import com.bazooka.overnote.model.Category
import com.bazooka.overnote.repository.CategoryRepository
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

  fun findCategoryById(id: Int): Category {
    val result: Optional<Category> = categoryRepository.findById(id)

    if (result.isEmpty)
        throw ResourceNotFoundException("Category with ID: $id was not found")

    return result.get()
  }

  fun updateCategoryById(id: Int, newCategory: Category): Category {
    val oldCategory = findCategoryById(id)
    val updatedCategory = oldCategory.copy(name = newCategory.name)
    return saveCategory(updatedCategory)
  }

  fun deleteCategoryById(id: Int) {
    val category = findCategoryById(id)
    return categoryRepository.delete(category)
  }
}
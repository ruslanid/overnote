package com.bazooka.overnote.service

import com.bazooka.overnote.exception.CategoryDuplicationException
import com.bazooka.overnote.exception.ResourceNotFoundException
import com.bazooka.overnote.model.Category
import com.bazooka.overnote.repository.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryService {

  @Autowired
  private lateinit var categoryRepository: CategoryRepository

  fun findAll(): Iterable<Category> =
          categoryRepository.findAll()

  fun saveCategory(category: Category): Category {
    try {
      return categoryRepository.save(category)
    } catch (e: DataIntegrityViolationException) {
      throw CategoryDuplicationException("Category already exists")
    }
  }

  fun findCategoryById(id: Int): Category {
    val result: Optional<Category> = categoryRepository.findById(id)

    if (result.isEmpty)
        throw ResourceNotFoundException("Category with ID: $id was not found")

    return result.get()
  }

  fun updateCategoryById(id: Int, newCategory: Category): Category {
    val oldCategory = findCategoryById(id)
    val updatedCategory = oldCategory.copy(title = newCategory.title)
    return saveCategory(updatedCategory)
  }

  fun deleteCategoryById(id: Int) {
    val category = findCategoryById(id)
    return categoryRepository.delete(category)
  }
}

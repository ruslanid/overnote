package com.bazooka.overnote.repository

import com.bazooka.overnote.model.Category
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository : CrudRepository<Category, Int> {
  fun findByTitle(s: String): Optional<Category>
}

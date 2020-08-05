package com.bazooka.overnote.repository

import com.bazooka.overnote.model.Category
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : CrudRepository<Category, Int> {

}
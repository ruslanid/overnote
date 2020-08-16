package com.bazooka.overnote.repository

import com.bazooka.overnote.model.Category
import com.bazooka.overnote.model.Note
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : CrudRepository<Note, Int> {

  fun findByCategory(category: Category): Iterable<Note>

  fun findFirst10ByOrderByUpdatedAtDesc(): Iterable<Note>

}

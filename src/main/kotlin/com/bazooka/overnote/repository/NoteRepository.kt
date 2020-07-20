package com.bazooka.overnote.repository

import com.bazooka.overnote.model.Note
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : CrudRepository<Note, Int> {
    fun findByTitle(title: String): Note?
}
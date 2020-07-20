package com.bazooka.overnote.repository

import com.bazooka.overnote.model.Note
import org.springframework.data.repository.CrudRepository

@Repository
interface NoteRepository : CrudRepository<Note, Int> {
    fun findByTitle(title: String): Note?
}
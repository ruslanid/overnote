package com.bazooka.overnote.repository

import com.bazooka.overnote.model.NoteCache
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteCacheRepository : CrudRepository<NoteCache?, String?> {
}
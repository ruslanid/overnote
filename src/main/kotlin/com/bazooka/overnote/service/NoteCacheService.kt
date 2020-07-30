package com.bazooka.overnote.service

import com.bazooka.overnote.exception.ResourceNotFoundException
import com.bazooka.overnote.model.NoteCache
import com.bazooka.overnote.repository.NoteCacheRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class NoteCacheService {

    @Autowired
    private lateinit var noteCacheRepository: NoteCacheRepository

    fun findAll(): MutableIterable<NoteCache?> =
            noteCacheRepository.findAll()

    fun saveNoteCache(note: NoteCache): NoteCache =
            noteCacheRepository.save(note)

    fun findNoteCacheById(noteId: String): NoteCache {
        val result: Optional<NoteCache?> = noteCacheRepository.findById(noteId)

        if (result.isEmpty)
            throw ResourceNotFoundException("NoteCache with ID: $noteId was not found")

        return result.get()
    }

    fun updateNoteCacheById(noteId: String, newNoteCache: NoteCache): NoteCache {
        val oldNoteCache = findNoteCacheById(noteId)
        val updatedNoteCache = oldNoteCache.copy(title = newNoteCache.title, body = newNoteCache.body)
        return saveNoteCache(updatedNoteCache)
    }

    fun deleteNoteCacheById(noteId: String) {
        val note = findNoteCacheById(noteId)
        return noteCacheRepository.delete(note)
    }
}
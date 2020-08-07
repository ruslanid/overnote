package com.bazooka.overnote.service

import com.bazooka.overnote.model.Note
import com.bazooka.overnote.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryNoteService {

  @Autowired
  private lateinit var categoryService: CategoryService

  @Autowired
  private  lateinit var noteService: NoteService

  @Autowired
  private lateinit var noteRepository: NoteRepository

    fun saveCategoryNote(categoryId: Int, note: Note): Note {
      note.category = categoryService.findCategoryById(categoryId)
      return noteRepository.save(note)
    }

    fun findNotesByCategoryId(categoryId: Int): Iterable<Note> {
      val category = categoryService.findCategoryById(categoryId)
      return noteRepository.findByCategory(category)
    }
}

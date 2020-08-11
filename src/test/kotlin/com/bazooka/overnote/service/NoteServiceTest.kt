package com.bazooka.overnote.service

import com.bazooka.overnote.exception.ResourceNotFoundException
import com.bazooka.overnote.model.Category
import com.bazooka.overnote.model.Note
import com.bazooka.overnote.repository.CategoryRepository
import com.bazooka.overnote.repository.NoteRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
@DisplayName("NoteService")
internal class NoteServiceTest {

  @Mock
  private lateinit var noteRepository: NoteRepository

  @Mock
  private lateinit var categoryRepository: CategoryRepository

  @InjectMocks
  private lateinit var noteService: NoteService

  private lateinit var notes: ArrayList<Note>

  @BeforeEach
  fun setUp() {
    val category = Category(1, "Others")
    val note1 = Note(1, "Title 1", "Description 1", null, null, category)
    val note2 = Note(2, "Title 2", "Description 2", null, null, category)
    notes = arrayListOf(note1, note2)
  }

  @Test
  @DisplayName("saveNote() - Default Category FOUND")
  fun saveNoteCategoryFound() {
    val category = Category(1, "Others")
    val note = Note(1, "Title 1", "Description 1", null, null, null)

    `when`(categoryRepository.findByTitle("Others")).thenReturn(Optional.of(category))
    `when`(noteRepository.save(note)).thenReturn(note)

    val dbNote = noteService.saveNote(note)

    assertNotNull(dbNote)
    assertEquals(notes[0], dbNote)
    assertEquals(1, dbNote.id)
    assertEquals("Title 1", dbNote.title)
    assertNotNull(dbNote.category)

    verify(categoryRepository, times(1)).findByTitle(anyString())
    verify(noteRepository, times(1)).save(any())

  }

  @Test
  @DisplayName("saveNote() - Default Category NOT FOUND")
  fun saveNoteCategoryNotFound() {
    val note = Note(1, "Title 1", "Description 1", null, null, null)

    `when`(categoryRepository.findByTitle("Others")).thenReturn(Optional.empty())

    val error = assertThrows<ResourceNotFoundException> { noteService.saveNote(note) }
    assertEquals("Default category with name 'Others' was not found.", error.message)

    verify(categoryRepository, times(1)).findByTitle(anyString())
    verify(noteRepository, never()).save(any())
  }

  @Test
  @DisplayName("findAll()")
  fun findAll() {
    `when`(noteRepository.findAll()).thenReturn(notes)

    val dbNotes = noteService.findAll()

    assertEquals(notes, dbNotes)
    assertEquals(notes.size, 2)

    verify(noteRepository, times(1)).findAll()
  }

  @Test
  @DisplayName("findNoteById() - Found")
  fun findNoteByIdFound() {
    `when`(noteRepository.findById(1)).thenReturn(Optional.of(notes[0]))

    val dbNote = noteService.findNoteById(1)

    assertNotNull(dbNote)
    assertEquals(notes[0], dbNote)
    assertEquals(1, dbNote.id)
    assertEquals("Title 1", dbNote.title)

    verify(noteRepository, times(1)).findById(anyInt())
  }

  @Test
  @DisplayName("findNoteById() - Not Found")
  fun findNoteByIdNotFound() {
    `when`(noteRepository.findById(10)).thenReturn(Optional.empty())

    val error = assertThrows<ResourceNotFoundException> { noteService.findNoteById(10) }
    assertEquals("Note with ID: 10 was not found", error.message)

    verify(noteRepository, times(1)).findById(anyInt())
  }

  @Test
  @DisplayName("updateNoteById() - Found")
  fun updateNoteByIdFound() {
    val newNote = Note(1, "title 10", "Description 10", null, null, Category(1, "Others"))

    `when`(noteRepository.findById(1)).thenReturn(Optional.of(notes[0]))
    `when`(noteRepository.save(newNote)).thenReturn(newNote)

    val updatedNote = noteService.updateNoteById(newNote, 1)

    assertNotNull(updatedNote)
    assertEquals("title 10", updatedNote.title)
    assertEquals("Description 10", updatedNote.description)

    verify(noteRepository, times(1)).findById(anyInt())
    verify(noteRepository, times(1)).save(any<Note>())
  }

  @Test
  @DisplayName("updateNoteById() = Not Found")
  fun updateNoteByIdNotFound() {
    `when`(noteRepository.findById(10)).thenReturn(Optional.empty())

    val note = Note(1, "title 1", "body 1", null, null, Category(1, "Others"))

    val error = assertThrows<ResourceNotFoundException> { noteService.updateNoteById(note, 10) }
    assertEquals("Note with ID: 10 was not found", error.message)

    verify(noteRepository, times(1)).findById(anyInt())
    verify(noteRepository, never()).delete(any())
  }

  @Test
  @DisplayName("deleteNoteByIdFound() - Found")
  fun deleteNoteByIdFound() {
    `when`(noteRepository.findById(1)).thenReturn(Optional.of(notes[0]))

    noteService.deleteNoteById(1)

    verify(noteRepository, times(1)).findById(anyInt())
    verify(noteRepository, times(1)).delete(any())
  }

  @Test
  @DisplayName("deleteNoteByIdFound() - Not Found")
  fun deleteNoteByIdNotFound() {
    `when`(noteRepository.findById(10)).thenReturn(Optional.empty())

    val error = assertThrows<ResourceNotFoundException> { noteService.deleteNoteById(10) }
    assertEquals("Note with ID: 10 was not found", error.message)

    verify(noteRepository, times(1)).findById(anyInt())
    verify(noteRepository, never()).delete(any())
  }
}

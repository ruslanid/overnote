package com.bazooka.overnote.controller

import com.bazooka.overnote.model.Note
import com.bazooka.overnote.service.EntityValidationService
import com.bazooka.overnote.service.NoteService
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(NoteController::class, EntityValidationService::class)
@DisplayName("NoteController")
internal class NoteControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var noteService: NoteService

    private lateinit var notes: ArrayList<Note>

    @BeforeEach
    fun setUp() {
        val note1 = Note(1, "Title 1", "Body 1", null, null)
        val note2 = Note(2, "Title 2", "Body 2", null, null)
        notes = arrayListOf(note1, note2)
    }

    @Test
    @DisplayName("getAllNotes()")
    fun getAllNotes() {
        `when`(noteService.findAll()).thenReturn(notes)

        mockMvc.perform(get("/api/notes"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$.length()", `is`(2)))
            .andExpect(jsonPath("$[0].id", `is`(1)))
            .andExpect(jsonPath("$[0].title", `is`("Title 1")))
            .andExpect(jsonPath("$[1].id", `is`(2)))
            .andExpect(jsonPath("$[1].title", `is`("Title 2")))

        verify(noteService, times(1)).findAll()
    }

    @Test
    @DisplayName("createNewNoteValid()")
    fun createNewNoteValid() {
        `when`(noteService.saveNote(any())).thenReturn(notes[0])

        mockMvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(Note(0, "Title 1", "Body 1", null, null))))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isMap)
            .andExpect(jsonPath("$.id", `is`(1)))
            .andExpect(jsonPath("$.title", `is`("Title 1")))

        verify(noteService, times(1)).saveNote(any())
    }

    @Test
    @DisplayName("createNewNoteNotValid()")
    fun createNewNoteNotValid() {
        // TODO: verify invocation of entityValidationService

         mockMvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(Note(0, null, "Body 1", null, null))))
             .andExpect(status().isBadRequest)
             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
             .andExpect(jsonPath("$").isMap)
             .andExpect(jsonPath("$.title", `is`("Title cannot be blank")))

        verify(noteService, never()).saveNote(any())
    }

    @Test
    @DisplayName("getNoteById()")
    fun getNoteById() {
        `when`(noteService.findNoteById(anyInt())).thenReturn(notes[0])

        mockMvc.perform(get("/api/notes/{id}", 1))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isMap)
            .andExpect(jsonPath("$.id", `is`(1)))
            .andExpect(jsonPath("$.title", `is`("Title 1")))

        verify(noteService, times(1)).findNoteById(anyInt())
    }

    @Test
    @DisplayName("updateNoteByIdValid()")
    fun updateNoteByIdValid() {
        val updatedNote = Note(1, "Title 22", "Body 22", null, null)

        `when`(noteService.updateNoteById(anyInt(), any())).thenReturn(updatedNote)

        mockMvc.perform(put("/api/notes/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(Note(1, "Title 22", "Body 22", null, null))))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isMap)
            .andExpect(jsonPath("$.id", `is`(1)))
            .andExpect(jsonPath("$.title", `is`("Title 22")))

        verify(noteService, times(1)).updateNoteById(anyInt(), any())
    }

    @Test
    @DisplayName("updateNoteByIdNotValid()")
    fun updateNoteByIdNotValid() {
        // TODO: verify invocation of entityValidationService

        mockMvc.perform(put("/api/notes/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(Note(1, "", "Body 1", null, null))))
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isMap)
            .andExpect(jsonPath("$.title", `is`("Title cannot be blank")))

        verify(noteService, never()).saveNote(any())
    }

    @Test
    @DisplayName("deleteNoteById()")
    fun deleteNoteById() {
        mockMvc.perform(delete("/api/notes/{id}", 1))
            .andExpect(status().isOk)

        verify(noteService, times(1)).deleteNoteById(anyInt())
    }

    private fun asJsonString(obj: Any?): String {
        return try {
            ObjectMapper().writeValueAsString(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
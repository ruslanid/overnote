package com.bazooka.overnote.model

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name="categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(nullable = false)
    @field: NotBlank(message = "Category name cannot be blank")
    val name: String = "Others"

) {

  @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "category")
  private val notes = mutableListOf<Note>()

  val getNotes get() = notes.toList()

  fun addNote(newNote: Note) =
      notes.add(newNote)

}
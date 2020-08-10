package com.bazooka.overnote.model

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name="categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false, unique = true)
    @field: NotBlank(message = "Category cannot be blank")
    val title: String?,

    @JsonBackReference
    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "category")
    var notes: MutableList<Note> = mutableListOf<Note>()

)

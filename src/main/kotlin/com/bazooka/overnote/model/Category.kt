package com.bazooka.overnote.model

import com.fasterxml.jackson.annotation.*
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
    val name: String?,

    @JsonBackReference
    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "category")
    var notes: MutableList<Note> = mutableListOf<Note>()

)
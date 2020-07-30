package com.bazooka.overnote.model

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name="notes")
data class Note(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(nullable = false)
    @field: NotBlank(message = "Title cannot be blank")
    val title: String?,

    @Column(length = 2000)
    val body: String?,

    @Column(name = "created_at", updatable = false)
    var createdAt: Date?,

    @Column(name="updated_at")
    var updatedAt: Date?

) {

    @PrePersist
    fun onCreate() {
        createdAt = Date()
        updatedAt = Date()
    }

    @PreUpdate
    fun onUpdate() {
        updatedAt = Date()
    }

}
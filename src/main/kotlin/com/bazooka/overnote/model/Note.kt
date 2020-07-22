package com.bazooka.overnote.model

import org.hibernate.validator.constraints.NotBlank
import java.util.*
import javax.persistence.*

@Entity
@Table(name="notes")
data class Note(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @Column(nullable = false)
    @get: NotBlank(message = "Title cannot be null")
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
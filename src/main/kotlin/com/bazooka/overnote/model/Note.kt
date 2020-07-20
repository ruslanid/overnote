package com.bazooka.overnote.model

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name="notes")
data class Note(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @Column(nullable = false)
    @NotBlank(message = "Title cannot be blank")
    val title: String,

    @Column(nullable = false, length = 2000)
    @NotBlank(message = "Body cannot be blank")
    val body: String,

    @Column(name = "created_at", updatable = false)
    var createdAt: Date,

    @Column(name="updated_at")
    var updatedAt: Date

) {

    @PrePersist
    fun onCreate() {
        createdAt = Date();
    }

    @PreUpdate
    fun onUpdate() {
        updatedAt = Date();
    }

}
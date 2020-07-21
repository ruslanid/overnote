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

    // TODO: Add model level bean (object) validation

    @Column(nullable = false)
//    @NotBlank(message = "Title can't be blank")
    val title: String,

    @Column(nullable = false, length = 2000)
//    @NotBlank(message = "Body can't be blank")
    val body: String,

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
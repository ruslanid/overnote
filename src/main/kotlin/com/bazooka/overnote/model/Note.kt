package com.bazooka.overnote.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name="notes")
data class Note(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    @field: NotBlank(message = "Title cannot be blank")
    val title: String?,

    @Column(length = 2000)
    val description: String?,

    @Column(name = "created_at", updatable = false)
    var createdAt: Date?,

    @Column(name="updated_at")
    var updatedAt: Date?,

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: Category? = null

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

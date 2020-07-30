package com.bazooka.overnote.model

import org.springframework.data.redis.core.RedisHash
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@RedisHash("NoteCache")
data class NoteCache(
        val title: String?,
        val body: String?
) {
    @Id
    var id: String? = null
}
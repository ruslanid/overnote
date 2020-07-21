package com.bazooka.overnote.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

@Service
class EntityValidationService {

    fun validateFields(result: BindingResult): ResponseEntity<MutableMap<String, String?>> {
        val errors =  mutableMapOf<String, String?>()

        for (error: FieldError in result.fieldErrors) {
            errors[error.field] = error.defaultMessage
        }

        return ResponseEntity.badRequest().body(errors);
    }
}
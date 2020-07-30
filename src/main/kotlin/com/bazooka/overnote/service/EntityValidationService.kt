package com.bazooka.overnote.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.validation.Errors
import org.springframework.validation.FieldError

@Service
class EntityValidationService {

    fun validateFields(errors: Errors): ResponseEntity<MutableMap<String, String?>> {
        val fieldErrorsMap =  mutableMapOf<String, String?>()

        for (error: FieldError in errors.fieldErrors) {
            fieldErrorsMap[error.field] = error.defaultMessage
        }

        return ResponseEntity.badRequest().body(fieldErrorsMap);
    }
}
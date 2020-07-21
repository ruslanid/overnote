package com.bazooka.overnote.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler
    fun handleResourceNotFound(e: ResourceNotFoundException, request: WebRequest): ResponseEntity<ResourceNotFoundResponse> {
        val errorResponse = ResourceNotFoundResponse(e.message!!)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}
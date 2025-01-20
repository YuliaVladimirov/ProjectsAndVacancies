package org.example.projects_and_vacancies.controllers

import org.example.projects_and_vacancies.exceptions.BadRequestException
import org.example.projects_and_vacancies.exceptions.DataNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(DataNotFoundException::class)
    fun handleException(exception: DataNotFoundException) : ResponseEntity<String>{
        return ResponseEntity(exception.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleException(exception: BadRequestException) : ResponseEntity<String>{
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleException(exception: RuntimeException) : ResponseEntity<String>{
        return ResponseEntity(exception.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception) : ResponseEntity<String>{
        return ResponseEntity(exception.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}
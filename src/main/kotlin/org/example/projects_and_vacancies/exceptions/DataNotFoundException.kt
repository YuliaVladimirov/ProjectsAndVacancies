package org.example.projects_and_vacancies.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
data class DataNotFoundException(override val message: String) : RuntimeException()





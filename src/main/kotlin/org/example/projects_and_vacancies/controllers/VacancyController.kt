package org.example.projects_and_vacancies.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.example.projects_and_vacancies.dtos.VacancyCreateRequest
import org.example.projects_and_vacancies.dtos.VacancyResponse
import org.example.projects_and_vacancies.dtos.VacancyUpdateRequest
import org.example.projects_and_vacancies.services.VacancyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
@Validated
class VacancyController(private var vacancyService: VacancyService) {

    @GetMapping("/projects/{id}/vacancies")
    @Operation(summary = "Getting vacancies", description = "Provides functionality for getting  all vacancies for certain project")
    fun getAllVacancies(
        @PathVariable
        @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1")
        @Parameter(description = "Project identifier")
        id: Long
    ): ResponseEntity<List<VacancyResponse>> =
        ResponseEntity(vacancyService.getAllVacancies(id), HttpStatus.OK)


    @GetMapping("/vacancies/{id}")
    @Operation(summary = "Getting a vacancy by id", description = "Provides functionality for getting a vacancy by id")
    fun getVacancyById(
        @PathVariable
        @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1")
        @Parameter(description = "Vacancy identifier")
        id: Long): ResponseEntity<VacancyResponse> =
        ResponseEntity(vacancyService.getVacancyById(id), HttpStatus.OK)


    @PostMapping("/projects/{id}/vacancies")
    @Operation(summary = "Creating a vacancy", description = "Provides functionality for creating a new vacancy for certain project")
    fun createVacancy(
        @PathVariable
        @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1")
        @Parameter(description = "Project identifier")
        id: Long,

        @RequestBody
        @Valid
        vacancyCreateRequest: VacancyCreateRequest
    ): ResponseEntity<VacancyResponse> =
        ResponseEntity(vacancyService.createVacancy(id, vacancyCreateRequest), HttpStatus.OK)


    @PutMapping("/vacancies/{id}")
    @Operation(summary = "Updating a vacancy", description = "Provides functionality for updating certain vacancy")
    fun updateVacancyById(
        @PathVariable
        @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1")
        @Parameter(description = "Vacancy identifier")
        id: Long,

        @RequestBody
        @Valid
        vacancyUpdateRequest: VacancyUpdateRequest
    ): ResponseEntity<VacancyResponse> =
        ResponseEntity(vacancyService.updateVacancyById(id, vacancyUpdateRequest), HttpStatus.OK)

    @DeleteMapping("/vacancies/{id}")
    @Operation(summary = "Deleting a vacancy", description = "Provides functionality for deleting a vacancy")
    fun deleteVacancyById(
        @PathVariable
        @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1")
        @Parameter(description = "Vacancy identifier")
        id: Long
    ): ResponseEntity<String> =
        ResponseEntity(vacancyService.deleteVacancyById(id), HttpStatus.OK)
}
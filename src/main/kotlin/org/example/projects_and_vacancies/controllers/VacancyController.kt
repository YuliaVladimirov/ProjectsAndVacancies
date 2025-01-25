package org.example.projects_and_vacancies.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.example.projects_and_vacancies.dtos.VacancyCreateRequest
import org.example.projects_and_vacancies.dtos.VacancyResponse
import org.example.projects_and_vacancies.dtos.VacancyUpdateRequest
import org.example.projects_and_vacancies.services.VacancyService
import org.springframework.data.web.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
@Validated
@Tag(name = "Vacancies controller", description = "Controller for managing vacancies")
class VacancyController(private var vacancyService: VacancyService) {

    @GetMapping("/projects/{id}/vacancies")
    @Operation(summary = "Getting vacancies", description = "Provides functionality for getting  all vacancies for certain project")
    fun getAllVacancies(
        @PathVariable(required = true)
        @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1")
        @Parameter(description = "Project identifier")
        id: Long,

        @RequestParam(defaultValue = "10")
        @Min(value = 1, message = "Invalid parameter: Size must be greater than or equal to 1")
        @Parameter(description = "Number of elements per one page")
        size: Int,

        @RequestParam(defaultValue = "0")
        @Min(value = 0, message = "Invalid parameter: Page numeration starts from 0")
        @Parameter(description = "Page number to display")
        page: Int,

        @RequestParam(defaultValue = "id")
        @Pattern(regexp = "^(id|name|field|experience|deadline)$", message = "Invalid value: Must be one of the following: id, name, field, experience, deadline")
        @Parameter(description = "The field the elements are sorted by")
        sortBy: String,

        @RequestParam(defaultValue = "ASC")
        @Pattern(regexp = "^(ASC|DESC|asc|desc)$", message = "Invalid order: Must be ASC or DESC (asc or desc)")//
        @Parameter(description = "Sorting parameters in ascending and descending order")
        order: String

    ): ResponseEntity<PagedModel<VacancyResponse>> =
        ResponseEntity(vacancyService.getAllVacancies(id, size, page, sortBy, order), HttpStatus.OK)



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
        ResponseEntity(vacancyService.createVacancy(id, vacancyCreateRequest), HttpStatus.CREATED)


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

    @DeleteMapping("/vacancies")
    @Operation(summary = "DO NOT TRY TO USE - demo reset, cleans the vacancies table", description = "DO NOT TRY TO USE - demo reset functionality")
    fun deleteAllVacancies(
        @RequestParam
        @NotBlank(message = "PASSWORD can't be empty")
        @Size(min = 5, message = "Invalid password: Must be more than 5 characters")
        @Parameter(description = "Password for authenticated access")
        password: String
    ): ResponseEntity<String> =
        ResponseEntity(vacancyService.deleteAllVacancies(password), HttpStatus.OK)
}
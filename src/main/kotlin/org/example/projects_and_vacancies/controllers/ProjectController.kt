package org.example.projects_and_vacancies.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.example.projects_and_vacancies.dtos.ProjectCreateRequest
import org.example.projects_and_vacancies.dtos.ProjectResponse
import org.example.projects_and_vacancies.dtos.ProjectUpdateRequest
import org.example.projects_and_vacancies.services.ProjectService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
@Validated
@Tag(name = "Project controller", description = "Controller for managing projects")
class ProjectController(private var projectService: ProjectService) {

    @GetMapping("/projects")
    @Operation(summary = "Getting projects", description = "Provides functionality for getting  all projects")
    fun getAllProjects(
        @RequestParam(defaultValue = "10")
        @Min(value = 1, message = "Invalid parameter: Size must be greater than or equal to 1")
        @Parameter(description = "Number of elements per one page")
        size: Int,

        @RequestParam(defaultValue = "0")
        @Min(value = 0, message = "Invalid parameter: Page numeration starts from 0")
        @Parameter(description = "Number of page to display")
        page: Int,

        @RequestParam(defaultValue = "id")
        @NotBlank(message = "SortBy field can't be empty.")
        @Parameter(description = "The field the elements are sorted by")
        sortBy: String,

        @RequestParam(defaultValue = "ASC")
        @Pattern(regexp = "^(ASC|DESC|asc|desc)$", message = "Invalid order: Must be ASC or DESC (asc or desc)")//
        @Parameter(description = "Sorting parameters in ascending and descending order")
        order: String
    ): ResponseEntity<Page<ProjectResponse>> =
        ResponseEntity(projectService.getAllProjects(size, page, sortBy, order), HttpStatus.OK)

    @GetMapping("/projects/{id}")
    @Operation(summary = "Getting a project by id", description = "Provides functionality for getting a project by id")
    fun getProjectById(
        @PathVariable
        @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1")
        @Parameter(description = "Project identifier")
        id: Long
    ): ResponseEntity<ProjectResponse> =
        ResponseEntity(projectService.getProjectById(id), HttpStatus.OK)

    @PostMapping("/projects")
    @Operation(summary = "Creating a project", description = "Provides functionality for creating a new project")
    fun createProject(
        @RequestBody
        @Valid
        projectCreateRequest: ProjectCreateRequest
    ): ResponseEntity<ProjectResponse> =
        ResponseEntity(projectService.createProject(projectCreateRequest), HttpStatus.CREATED)

    @PutMapping("/projects/{id}")
    @Operation(summary = "Updating a project", description = "Provides functionality for updating certain project")
    fun updateProjectById(
        @PathVariable
        @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1")
        @Parameter(description = "Project identifier")
        id: Long,

        @RequestBody
        @Valid
        projectUpdateRequest: ProjectUpdateRequest
    ): ResponseEntity<ProjectResponse> =
        ResponseEntity(projectService.updateProjectById(id, projectUpdateRequest), HttpStatus.OK)

    @DeleteMapping("/projects/{id}")
    @Operation(summary = "Deleting a project", description = "Provides functionality for deleting a project")
    fun deleteProjectById(
        @PathVariable
        @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1")
        @Parameter(description = "Project identifier")
        id: Long
    ): ResponseEntity<String> =
        ResponseEntity(projectService.deleteProjectById(id), HttpStatus.OK)

    @DeleteMapping("/projects")
    @Operation(summary = "DO NOT TRY TO USE - demo reset, cleans the projects table", description = "DO NOT TRY TO USE - demo reset functionality")
    fun deleteAllProjects(
        @RequestParam
        @NotBlank(message = "PASSWORD can't be empty")
        @Size(min = 5, message = "Invalid password: Must be more than 5 characters")
        @Parameter(description = "Password for authenticated access")
        password: String
    ): ResponseEntity<String> =
        ResponseEntity(projectService.deleteAllProjects(password), HttpStatus.OK)
}
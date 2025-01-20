package org.example.projects_and_vacancies.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat

@Schema(description = "Request for creating a new project")
data class ProjectCreateRequest(

    @JsonProperty("name")
    @field:NotBlank(message = "NAME can't be empty")
    @field:Size(min = 2, max = 50, message = "Invalid parameter: Must be of 2 - 50 characters")
    @Schema(description = "Name of the project", example = "Creating visual materials for social media")
    var name: String,

    @JsonProperty("field")
    @field:NotBlank(message = "FIELD can't be empty")
    @field:Size(min = 2, max = 50, message = "Invalid parameter: Must be of 2 - 50 characters")
    @Schema(description = "Field of activity", example = "Design")
    var field: String,

    @JsonProperty("experience")
    @field:NotBlank(message = "EXPERIENCE can't be empty")
    @field:Size(min = 2, max = 50, message = "Invalid parameter: Must be of 2 - 50 characters")
    @Schema(description = "Sufficient experience for the project", example = "More than 2 years")
    var experience: String,

    @JsonProperty("deadline")
    @field:NotBlank(message = "DEADLINE can't be empty")
    @field:DateTimeFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Project deadline date", example = "22-11-2024")
    var deadline: String,

    @JsonProperty("description")
    @field:NotBlank(message = "DESCRIPTION field can't be empty")
    @field:Size(min = 2, max = 150, message = "Invalid project description: Must be of 2 - 150 characters")
    @Schema(description = "Description of the project", example = "This project is designed to develop eye-catching and engaging visual materials for our social media platforms. The goal is to create content that aligns with our brand identity and effectively captures our audience's attention.")
    var description: String,
)
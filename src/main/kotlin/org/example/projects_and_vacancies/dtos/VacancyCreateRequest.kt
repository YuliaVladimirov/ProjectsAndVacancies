package org.example.projects_and_vacancies.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Request for creating a new vacancy")
data class VacancyCreateRequest (

    @JsonProperty("name")
    @field:NotBlank(message = "NAME field can't be empty")
    @field:Size(min = 2, max = 150, message = "Invalid parameter: Must be of 2 - 150 characters")
    @Schema(description = "Name of the vacancy", example = "Front-End Developer")
    var name: String,

    @JsonProperty("field")
    @field:NotBlank(message = "FIELD can't be empty")
    @field:Size(min = 2, max = 150, message = "Invalid parameter: Must be of 2 - 150 characters")
    @Schema(description = "Field of activity", example = "Design")
    var field: String,

    @JsonProperty("experience")
    @field:NotBlank(message = "EXPERIENCE can't be empty")
    @field:Size(min = 2, max = 150, message = "Invalid parameter: Must be of 2 - 150 characters")
    @Schema(description = "Sufficient experience for the vacancy", example = "More than 2 years")
    var experience: String,

    @JsonProperty("country")
    @field:NotBlank(message = "COUNTRY can't be empty")
    @field:Size(min = 2, max = 150, message = "Invalid parameter: Must be of 2 - 150 characters")
    @Schema(description = "Location of the proposed vacancy", example = "USA")
    var country: String,

    @JsonProperty("description")
    @field:NotBlank(message = "DESCRIPTION field can't be empty")
    @field:Size(min = 2, message = "Invalid vacancy description: Must be more than 2 characters")
    @Schema(description = "Description of the project", example = "We are looking for a creative and detail-oriented designer to develop eye-catching and engaging visual materials for our social media platforms.")
    var description: String,
)
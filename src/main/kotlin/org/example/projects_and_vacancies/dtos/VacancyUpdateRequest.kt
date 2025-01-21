package org.example.projects_and_vacancies.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

@Schema(description = "Request for updating a vacancy")
data class VacancyUpdateRequest(

    @JsonProperty("name")
    @field:Size(min = 2, max = 150, message = "Invalid parameter: Must be of 2 - 150 characters")
    @Schema(description = "Name of the vacancy", example = "Front-End Developer")
    var name: String?,

    @JsonProperty("field")
    @field:Size(min = 2, max = 150, message = "Invalid parameter: Must be of 2 - 150 characters")
    @Schema(description = "Field of activity", example = "Design")
    var field: String?,

    @JsonProperty("experience")
    @field:Size(min = 2, max = 150, message = "Invalid parameter: Must be of 2 - 150 characters")
    @Schema(description = "Sufficient experience for the vacancy", example = "More than 2 years")
    var experience: String?,

    @JsonProperty("country")
    @field:Size(min = 2, max = 150, message = "Invalid parameter: Must be of 2 - 150 characters")
    @Schema(description = "Location of the proposed vacancy", example = "USA")
    var country: String?,

    @JsonProperty("description")
    @field:Size(min = 2, message = "Invalid vacancy description: Must be more than 2 characters")
    @Schema(description = "Description of the project", example = "We are looking for a creative and detail-oriented designer to develop eye-catching and engaging visual materials for our social media platforms.")
    var description: String?,
)
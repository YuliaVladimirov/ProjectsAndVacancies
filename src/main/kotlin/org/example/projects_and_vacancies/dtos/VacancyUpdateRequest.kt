package org.example.projects_and_vacancies.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request for updating a vacancy")
data class VacancyUpdateRequest(

    @JsonProperty("name")
    @Schema(description = "Name of the vacancy", example = "Front-End Developer")
    var name: String?,

    @JsonProperty("field")
    @Schema(description = "Field of activity", example = "Design")
    var field: String?,

    @JsonProperty("experience")
    @Schema(description = "Sufficient experience for the vacancy", example = "More than 2 years")
    var experience: String?,

    @JsonProperty("country")
    @Schema(description = "Location of the proposed vacancy", example = "USA")
    var country: String?,

    @JsonProperty("description")
    @Schema(description = "Description of the project", example = "We are looking for a creative and detail-oriented designer to develop eye-catching and engaging visual materials for our social media platforms.")
    var description: String?,
)
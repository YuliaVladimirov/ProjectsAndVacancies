package org.example.projects_and_vacancies.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Project response")
data class ProjectResponse(

    @JsonProperty("id")
    @Schema(description = "Unique project id", example = "1")
    val id: Long,

    @JsonProperty("name")
    @Schema(description = "Name of the project", example = "Creating visual materials for social media")
    var name: String,

    @JsonProperty("field")
    @Schema(description = "Field of activity", example = "Design")
    var field: String,


    @JsonProperty("experience")
    @Schema(description = "Sufficient experience for the project", example = "More than 2 years")
    var experience: String,

    @JsonProperty("deadline")
    @Schema(description = "Project deadline date", example = "22.11.2024")
    var deadline: String,

    @JsonProperty("description")
    @Schema(description = "Description of the project", example = "This project is designed to develop eye-catching and engaging visual materials for our social media platforms. The goal is to create content that aligns with our brand identity and effectively captures our audience's attention.")
    var description: String,

)
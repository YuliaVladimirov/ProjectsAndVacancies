package org.example.projects_and_vacancies.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.example.projects_and_vacancies.dtos.ProjectCreateRequest
import org.example.projects_and_vacancies.dtos.ProjectResponse
import org.example.projects_and_vacancies.dtos.ProjectUpdateRequest
import org.example.projects_and_vacancies.services.ProjectService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.web.PagedModel
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(controllers = [ProjectController::class])
class ProjectControllerTest(@Autowired private val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var projectServiceMock: ProjectService
    private val mapper = jacksonObjectMapper()

    @Test
    fun getAllProjects() {
        val projectResponse = ProjectResponse(
            1,
            "ProjectOne",
            "Programming",
            "More than 1 year",
            "23-01-2025",
            "Some description"
        )

        val projectPage: Page<ProjectResponse> = PageImpl(mutableListOf(projectResponse))

        val size = 1
        val page = 0
        val sortBy = "id"
        val order = "ASC"

        val pagedModel = PagedModel(projectPage)

        every{ projectServiceMock.getAllProjects(size, page, sortBy, order) } returns pagedModel

        mockMvc.perform(MockMvcRequestBuilders.get("/projects?size=1&page=0&sortBy=id&order=ASC"))

            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        io.mockk.verify(atLeast = 1) {
            projectServiceMock.getAllProjects(size, page, sortBy, order)
        }

    }

    @Test
    fun getProjectById() {

        val id: Long = 1
        val projectResponse = ProjectResponse(
            1,
            "ProjectOne",
            "Programming",
            "More than 1 year",
            "23-01-2025",
            "Some description"
        )

        every { projectServiceMock.getProjectById(id) } returns projectResponse

            mockMvc.perform(MockMvcRequestBuilders.get("/projects/{id}", id))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))

        io.mockk.verify(atLeast = 1) {
            projectServiceMock.getProjectById(id)
        }
    }


    @Test
    fun createProject() {
        val projectCreateRequest = ProjectCreateRequest(

            "ProjectOne",
            "Programming",
            "More than 1 year",
            "23-01-2025",
            "Some description"
        )

        val projectResponse = ProjectResponse(
            1,
            "ProjectOne",
            "Programming",
            "More than 1 year",
            "23-01-2025",
            "Some description"
        )

        every { projectServiceMock.createProject(projectCreateRequest) } returns projectResponse

        mockMvc.perform(MockMvcRequestBuilders.post("/projects")
            .content(mapper.writeValueAsString(projectCreateRequest)).contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value(projectCreateRequest.name))

        io.mockk.verify(atLeast = 1) {
            projectServiceMock.createProject(projectCreateRequest)
        }
    }

    @Test
    fun updateProjectById() {
        val id: Long = 1

        val projectUpdateRequest = ProjectUpdateRequest(

            "ProjectOne",
            "Programming",
            "More than 1 year",
            "23-01-2025",
            "Some description"
        )

        val projectResponse = ProjectResponse(
            1,
            "ProjectOne",
            "Programming",
            "More than 1 year",
            "23-01-2025",
            "Some description"
        )
        every { projectServiceMock.updateProjectById(id,projectUpdateRequest) } returns projectResponse

        mockMvc.perform(MockMvcRequestBuilders.put("/projects/{id}", id)
            .content(mapper.writeValueAsString(projectUpdateRequest)).contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value(projectUpdateRequest.name))

        io.mockk.verify(atLeast = 1) {
            projectServiceMock.updateProjectById(id,projectUpdateRequest)
        }
    }

    @Test
    fun deleteProjectById() {

        val id: Long = 1
        val message = "text/plain;charset=UTF-8"

        every { projectServiceMock.deleteProjectById(id) } returns message

        mockMvc.perform(MockMvcRequestBuilders.delete("/projects/{id}", id))

            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.parseMediaType(message)))

        io.mockk.verify(atLeast = 1) {
            projectServiceMock.deleteProjectById(id)
        }
    }
}
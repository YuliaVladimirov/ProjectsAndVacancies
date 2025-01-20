package org.example.projects_and_vacancies.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.example.projects_and_vacancies.dtos.VacancyCreateRequest
import org.example.projects_and_vacancies.dtos.VacancyResponse
import org.example.projects_and_vacancies.dtos.VacancyUpdateRequest
import org.example.projects_and_vacancies.services.VacancyService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [VacancyController::class])
class VacancyControllerTest(@Autowired private val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var vacancyServiceMock: VacancyService
    private val mapper = jacksonObjectMapper()

    @Test
    fun getAllVacancies() {

        val id: Long = 1

        val vacancyResponse = VacancyResponse(
            1,
            "VacancyOne",
            "Back-End-Developer",
            "More than 1 year",
            "USA",
            "Some description"
        )

        val projectList: List<VacancyResponse> = listOf(vacancyResponse)

        every{ vacancyServiceMock.getAllVacancies(id) } returns projectList;

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{id}/vacancies", id))

            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size()").value(projectList.size))

        io.mockk.verify(atLeast = 1) {
            vacancyServiceMock.getAllVacancies(id)
        }
    }

    @Test
    fun getVacancyById() {

        val id: Long = 1

        val vacancyResponse = VacancyResponse(
            1,
            "VacancyOne",
            "Back-End-Developer",
            "More than 1 year",
            "USA",
            "Some description"
        )

        every { vacancyServiceMock.getVacancyById(id) } returns vacancyResponse;

        mockMvc.perform(MockMvcRequestBuilders.get("/vacancies/{id}", id))
        .andExpect(status().isOk)
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(id))

        io.mockk.verify(atLeast = 1) {
            vacancyServiceMock.getVacancyById(id)
        }
    }

    @Test
    fun createVacancy() {
        val id: Long = 1

        val vacancyCreateRequest = VacancyCreateRequest(

            "VacancyOne",
            "Back-End-Developer",
            "More than 1 year",
            "USA",
            "Some description"
        )

        val vacancyResponse = VacancyResponse(
            1,
            "VacancyOne",
            "Back-End-Developer",
            "More than 1 year",
            "USA",
            "Some description"
        )

        every { vacancyServiceMock.createVacancy(id, vacancyCreateRequest) } returns vacancyResponse;

        mockMvc.perform(MockMvcRequestBuilders.post("/projects/{id}/vacancies", id)
            .content(mapper.writeValueAsString(vacancyCreateRequest)).contentType(MediaType.APPLICATION_JSON))

        .andExpect(status().isOk)
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name").value(vacancyCreateRequest.name))

        io.mockk.verify(atLeast = 1) {
            vacancyServiceMock.createVacancy(id, vacancyCreateRequest)
        }
    }

    @Test
    fun updateVacancyById() {
        val id: Long = 1

        val vacancyUpdateRequest = VacancyUpdateRequest(

            "VacancyOne",
            "Back-End-Developer",
            "More than 1 year",
            "Germany",
            "Some description"
        )

        val vacancyResponse = VacancyResponse(
            1,
            "VacancyOne",
            "Back-End-Developer",
            "More than 1 year",
            "USA",
            "Some description"
        )

        every { vacancyServiceMock.updateVacancyById(id,vacancyUpdateRequest) } returns vacancyResponse;

        mockMvc.perform(MockMvcRequestBuilders.put("/vacancies/{id}", id)
            .content(mapper.writeValueAsString(vacancyUpdateRequest)).contentType(MediaType.APPLICATION_JSON))

        .andExpect(status().isOk)
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.name").value(vacancyUpdateRequest.name))

        io.mockk.verify(atLeast = 1) {
            vacancyServiceMock.updateVacancyById(id,vacancyUpdateRequest)
        }
    }

    @Test
    fun deleteVacancyById() {
        val id: Long = 1
        val message = "text/plain;charset=UTF-8"

        every { vacancyServiceMock.deleteVacancyById(id) } returns message;
        mockMvc.perform(MockMvcRequestBuilders.delete("/vacancies/{id}", id))
        .andExpect(status().isOk)
        .andExpect(content().contentType(MediaType.parseMediaType(message)))

        io.mockk.verify(atLeast = 1) {
            vacancyServiceMock.deleteVacancyById(id)
        }
    }
}
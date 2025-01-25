package org.example.projects_and_vacancies.services

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.example.projects_and_vacancies.dtos.VacancyCreateRequest
import org.example.projects_and_vacancies.dtos.VacancyResponse
import org.example.projects_and_vacancies.dtos.VacancyUpdateRequest
import org.example.projects_and_vacancies.entities.Project
import org.example.projects_and_vacancies.entities.Vacancy
import org.example.projects_and_vacancies.exceptions.DataNotFoundException
import org.example.projects_and_vacancies.repositories.ProjectRepository
import org.example.projects_and_vacancies.repositories.VacancyRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.web.PagedModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.test.assertNotNull

@ExtendWith(MockKExtension::class)
class VacancyServiceTest {

    @RelaxedMockK
    private lateinit var vacancyRepositoryMock: VacancyRepository

    @RelaxedMockK
    private lateinit var projectRepositoryMock: ProjectRepository

    @InjectMockKs
    private lateinit var vacancyServiceMock: VacancyService


    private lateinit var project: Project
    private lateinit var vacancy: Vacancy
    private lateinit var vacancyCreateRequest: VacancyCreateRequest
    private lateinit var vacancyUpdateRequest: VacancyUpdateRequest
    private lateinit var vacancyResponse: VacancyResponse

    @BeforeEach
    fun setUp() {

        MockKAnnotations.init(this)
        project = Project(
            1,
            "ProjectOne",
            "Programming",
            "More than 1 year",
            LocalDate.parse("23-01-2025", DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            "Some description"
        )

        MockKAnnotations.init(this)
        vacancy = Vacancy(
            1,
            "VacancyOne",
            "Back-End-Developer",
            "More than 1 year",
            "USA",
            "Some description",
            project
        )

        MockKAnnotations.init(this)
        vacancyCreateRequest = VacancyCreateRequest(

            "VacancyOne",
            "Back-End-Developer",
            "More than 1 year",
            "USA",
            "Some description"
        )

        MockKAnnotations.init(this)
        vacancyUpdateRequest = VacancyUpdateRequest(

            "VacancyOne",
            "Back-End-Developer",
            "More than 1 year",
            "Germany",
            "Some description"
        )

        MockKAnnotations.init(this)
        vacancyResponse = VacancyResponse(
            1,
            "VacancyOne",
            "Back-End-Developer",
            "More than 1 year",
            "USA",
            "Some description"
        )

    }

    @Test
    fun convertEntityToResponse() {
        val actualVacancyResponse: VacancyResponse = vacancyServiceMock.convertEntityToResponse(vacancy)
        assertNotNull(actualVacancyResponse)
        assertThat(actualVacancyResponse).hasSameClassAs(vacancyResponse)
    }

    @Test
    fun convertCreateRequestToEntity() {

        val actualVacancy: Vacancy = vacancyServiceMock.convertCreateRequestToEntity(vacancyCreateRequest, project)
        assertThat(actualVacancy).hasSameClassAs(vacancy)
    }

    @Test
    fun createVacancy() {
        val id: Long = 1
        val projectOptional: Optional<Project> = Optional.ofNullable(project)

        every { projectRepositoryMock.findById(id) } returns projectOptional
        val foundProject: Optional<Project> = projectRepositoryMock.findById(id)
        assertThat(foundProject.get().id).isEqualTo(id)

        every { vacancyRepositoryMock.save(any(Vacancy::class)) } returns vacancy
        val actualVacancyResponse: VacancyResponse? = vacancyServiceMock.createVacancy(id, vacancyCreateRequest)

        if (actualVacancyResponse != null) {
            assertThat(actualVacancyResponse).hasSameClassAs(vacancyResponse)
            assertThat(actualVacancyResponse.name).isEqualTo(vacancyCreateRequest.name)
        }

        verify(atLeast = 1) { projectRepositoryMock.findById(id) }
        verify(atLeast = 1) { vacancyRepositoryMock.save(any(Vacancy::class)) }
    }

    @Test
    fun createVacancyThrowsException() {
        val id: Long = 2
        val projectOptional: Optional<Project> = Optional.ofNullable(null)
        every { projectRepositoryMock.findById(id) } returns projectOptional

        val exception =
            assertThrows<DataNotFoundException> { vacancyServiceMock.createVacancy(id, vacancyCreateRequest) }
        assertThat(exception.message).isEqualTo("Project with ID: $id does not exist!")

        verify(atLeast = 1) { projectRepositoryMock.findById(id) }
    }

    @Test
    fun getAllVacancies() {
        val id: Long = 1
        val size = 1
        val page = 0
        val sortBy = "id"
        val order = "ASC"

        val vacancyList: MutableList<Vacancy> = mutableListOf(vacancy)
        project.vacancies = vacancyList
        val projectOptional: Optional<Project> = Optional.ofNullable(project)

        every { projectRepositoryMock.findById(id) } returns projectOptional

        val foundProject: Optional<Project> = projectRepositoryMock.findById(id)
        assertThat(foundProject.get().id).isEqualTo(id)

        val vacancyPage: Page<VacancyResponse> = PageImpl(mutableListOf(vacancyResponse))



        val pagedModel = PagedModel(vacancyPage)

        val actualPagedModel: PagedModel<VacancyResponse> = vacancyServiceMock.getAllVacancies(id, size, page, sortBy, order)

        assertNotNull(actualPagedModel)
        assertThat(actualPagedModel).hasSameClassAs(pagedModel)

        verify(atLeast = 1) { projectRepositoryMock.findById(id) }
    }

    @Test
    fun getAllVacanciesThrowsException() {
        val id: Long = 2
        val size = 1
        val page = 0
        val sortBy = "id"
        val order = "ASC"
        val projectOptional: Optional<Project> = Optional.ofNullable(null)
        every { projectRepositoryMock.findById(id) } returns projectOptional

        val exception = assertThrows<DataNotFoundException> { vacancyServiceMock.getAllVacancies(id, size, page, sortBy, order) }
        assertThat(exception.message).isEqualTo("Project with ID: $id does not exist!")
        verify(atLeast = 1) { projectRepositoryMock.findById(id) }
    }

    @Test
    fun getVacancyById() {
        val id: Long = 1
        val vacancyOptional: Optional<Vacancy> = Optional.ofNullable(vacancy)

        every { vacancyRepositoryMock.findById(id) } returns vacancyOptional

        val actualVacancyResponse: VacancyResponse? = vacancyServiceMock.getVacancyById(id)
        if (actualVacancyResponse != null) {
            assertThat(actualVacancyResponse.id).isEqualTo(id)
        }
        verify(atLeast = 1) { vacancyRepositoryMock.findById(id) }
    }

    @Test
    fun getVacancyByIdThrowsException() {
        val id: Long = 2
        val vacancyOptional: Optional<Vacancy> = Optional.ofNullable(null)
        every { vacancyRepositoryMock.findById(id) } returns vacancyOptional

        val exception = assertThrows<DataNotFoundException> { vacancyServiceMock.getVacancyById(id) }
        assertThat(exception.message).isEqualTo("Vacancy with ID: $id does not exist!")
        verify(atLeast = 1) { vacancyRepositoryMock.findById(id) }
    }

    @Test
    fun updateVacancyById() {
        val id: Long = 1
        val vacancyOptional: Optional<Vacancy> = Optional.ofNullable(vacancy)

        every { vacancyRepositoryMock.findById(id) } returns vacancyOptional
        every { vacancyRepositoryMock.save(any(Vacancy::class)) } returns vacancy

        val actualVacancyResponse: VacancyResponse? = vacancyServiceMock.updateVacancyById(id, vacancyUpdateRequest)

        if (actualVacancyResponse != null) {
            assertThat(actualVacancyResponse.name).isEqualTo(vacancyUpdateRequest.name)
        }
        verify(atLeast = 1) { vacancyRepositoryMock.findById(id) }
        verify(atLeast = 1) { vacancyRepositoryMock.save(any(Vacancy::class)) }
    }

    @Test
    fun updateVacancyByIdThrowsException() {
        val id: Long = 2
        val vacancyOptional: Optional<Vacancy> = Optional.ofNullable(null)
        every { vacancyRepositoryMock.findById(id) } returns vacancyOptional

        val exception =
            assertThrows<DataNotFoundException> { vacancyServiceMock.updateVacancyById(id, vacancyUpdateRequest) }
        assertThat(exception.message).isEqualTo("Vacancy with ID: $id does not exist!")

        verify(atLeast = 1) { vacancyRepositoryMock.findById(id) }
        verify(atLeast = 0) { vacancyRepositoryMock.save(any(Vacancy::class)) }
    }

    @Test
    fun deleteVacancyById() {
        val id: Long = 1

        every { vacancyRepositoryMock.existsById(id) } returns true
        vacancyServiceMock.deleteVacancyById(id)

        verify(atLeast = 1) { vacancyRepositoryMock.existsById(id) }
        verify(atLeast = 1) { vacancyRepositoryMock.deleteById(id) }
    }

    @Test
    fun deleteVacancyByIdThrowsException() {
        val id: Long = 2

        every { vacancyRepositoryMock.existsById(id) } returns false

        val exception =
            assertThrows<DataNotFoundException> { vacancyServiceMock.deleteVacancyById(id) }
        assertThat(exception.message).isEqualTo("Vacancy with ID: $id does not exist!")

        verify(atLeast = 1) { vacancyRepositoryMock.existsById(id) }
        verify(atLeast = 0) { vacancyRepositoryMock.delete(any(Vacancy::class)) }
    }
}
package org.example.projects_and_vacancies.services

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.example.projects_and_vacancies.dtos.ProjectCreateRequest
import org.example.projects_and_vacancies.dtos.ProjectResponse
import org.example.projects_and_vacancies.dtos.ProjectUpdateRequest
import org.example.projects_and_vacancies.entities.Project
import org.example.projects_and_vacancies.exceptions.DataNotFoundException
import org.example.projects_and_vacancies.repositories.ProjectRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.test.assertNotNull

@ExtendWith(MockKExtension::class)
class ProjectServiceTest {

    @RelaxedMockK
    private lateinit var projectRepositoryMock: ProjectRepository

    @InjectMockKs
    private lateinit var projectServiceMock: ProjectService

    private lateinit var project: Project
    private lateinit var projectCreateRequest: ProjectCreateRequest
    private lateinit var projectUpdateRequest: ProjectUpdateRequest
    private lateinit var projectResponse: ProjectResponse


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
        projectCreateRequest = ProjectCreateRequest(

            "ProjectOne",
            "Programming",
            "More than 1 year",
            "23-01-2025",
            "Some description"
        )

        MockKAnnotations.init(this)
        projectUpdateRequest = ProjectUpdateRequest(

            "ProjectOne",
            "Programming",
            "More than 1 year",
            "23-01-2025",
            "Some description"
        )

        MockKAnnotations.init(this)
        projectResponse = ProjectResponse(
            1,
            "ProjectOne",
            "Programming",
            "More than 1 year",
            "23-01-2025",
            "Some description"
        )
    }

    @Test
    fun convertEntityToResponse() {
        val actualProjectResponse: ProjectResponse = projectServiceMock.convertEntityToResponse(project)
        assertNotNull(actualProjectResponse)
        assertThat(actualProjectResponse).hasSameClassAs(projectResponse)
    }

    @Test
    fun convertRequestToEntity() {

        val actualProject: Project = projectServiceMock.convertRequestToEntity(projectCreateRequest)
        assertNotNull(actualProject)
        assertThat(actualProject).hasSameClassAs(project)
    }

    @Test
    fun createProject() {

        every { projectRepositoryMock.save(any(Project::class)) } returns project
        val actualProjectResponse: ProjectResponse? = projectServiceMock.createProject(projectCreateRequest)

        if (actualProjectResponse != null) {
            assertThat(actualProjectResponse).hasSameClassAs(projectResponse)
            assertThat(actualProjectResponse.name).isEqualTo(projectCreateRequest.name)
        }

        verify(atLeast = 1) { projectRepositoryMock.save(any(Project::class)) }
    }

    @Test
    fun getAllProjects() {
        val pageable: Pageable = PageRequest.of(0, 1, Sort.Direction.fromString("asc"), "id")
        val projectPage: Page<Project> = PageImpl(mutableListOf(project))

        every { projectRepositoryMock.findAll(pageable) } returns projectPage
        val actualPage: Page<ProjectResponse>? = projectServiceMock.getAllProjects(1, 0, "id", "asc")
        assertNotNull(actualPage)
        assertThat(actualPage).hasSameClassAs(projectPage)

        verify(atLeast = 1) { projectRepositoryMock.findAll(pageable) }
    }

    @Test
    fun getProjectById() {
        val id: Long = 1
        val projectOptional: Optional<Project> = Optional.ofNullable(project)

        every { projectRepositoryMock.findById(id) } returns projectOptional

        val actualProjectResponse: ProjectResponse? = projectServiceMock.getProjectById(id)
        if (actualProjectResponse != null) {
            assertThat(actualProjectResponse.id).isEqualTo(id)
        }
        verify(atLeast = 1) { projectRepositoryMock.findById(id) }
    }


    @Test
    fun getProjectByIdThrowsException() {
        val id: Long = 2
        val projectOptional: Optional<Project> = Optional.ofNullable(null)
        every { projectRepositoryMock.findById(id) } returns projectOptional

        val exception = assertThrows<DataNotFoundException> { projectServiceMock.getProjectById(id) }
        assertThat(exception.message).isEqualTo("Project with ID: $id does not exist!")
        verify(atLeast = 1) { projectRepositoryMock.findById(id) }
    }


    @Test
    fun updateProjectById() {
        val id: Long = 1
        val projectOptional: Optional<Project> = Optional.ofNullable(project)

        every { projectRepositoryMock.findById(id) } returns projectOptional
        every { projectRepositoryMock.save(any(Project::class)) } returns project

        val actualProjectResponse: ProjectResponse? = projectServiceMock.updateProjectById(id, projectUpdateRequest)

        if (actualProjectResponse != null) {
            assertThat(actualProjectResponse.name).isEqualTo(projectUpdateRequest.name)
        }
        verify(atLeast = 1) { projectRepositoryMock.findById(id) }
        verify(atLeast = 1) { projectRepositoryMock.save(any(Project::class)) }
    }

    @Test
    fun updateProjectByIdThrowsException() {
        val id: Long = 2
        val projectOptional: Optional<Project> = Optional.ofNullable(null)
        every { projectRepositoryMock.findById(id) } returns projectOptional

        val exception = assertThrows<DataNotFoundException> { projectServiceMock.updateProjectById(id, projectUpdateRequest) }
        assertThat(exception.message).isEqualTo("Project with ID: $id does not exist!")

        verify(atLeast = 1) { projectRepositoryMock.findById(id) }
        verify (atLeast = 0) { projectRepositoryMock.save(any(Project::class)) }
    }

    @Test
    fun deleteProjectById() {
        val id: Long = 1
        val projectOptional: Optional<Project> = Optional.ofNullable(project)

        every { projectRepositoryMock.findById(id) } returns projectOptional
        projectServiceMock.deleteProjectById(id)

        verify(atLeast = 1) { projectRepositoryMock.findById(id) }
        verify(atLeast = 1) { projectRepositoryMock.delete(any(Project::class)) }
    }

    @Test
    fun deleteProjectByIdThrowsException() {
        val id: Long = 2
        val projectOptional: Optional<Project> = Optional.ofNullable(null)
        every { projectRepositoryMock.findById(id) } returns projectOptional

        val exception = assertThrows<DataNotFoundException> { projectServiceMock.deleteProjectById(id) }
        assertThat(exception.message).isEqualTo("Project with ID: $id does not exist!")
        verify(atLeast = 1) { projectRepositoryMock.findById(id) }
        verify(atLeast = 0) { projectRepositoryMock.delete(any(Project::class))  }
    }
}
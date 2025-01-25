package org.example.projects_and_vacancies.services

import lombok.extern.slf4j.Slf4j
import org.example.projects_and_vacancies.dtos.ProjectCreateRequest
import org.example.projects_and_vacancies.dtos.ProjectResponse
import org.example.projects_and_vacancies.dtos.ProjectUpdateRequest
import org.example.projects_and_vacancies.entities.Project
import org.example.projects_and_vacancies.exceptions.BadRequestException
import org.example.projects_and_vacancies.exceptions.DataNotFoundException
import org.example.projects_and_vacancies.repositories.ProjectRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Service
@Slf4j
class ProjectService(
    private var projectRepository: ProjectRepository
) {

    private val pass: String = System.getenv("CLEAN_PASSWORD") ?: "-"

    fun convertEntityToResponse(project: Project): ProjectResponse {
        return ProjectResponse(
            project.id,
            project.name,
            project.field,
            project.experience,
            project.deadline.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            project.description,
        )
    }

    fun convertRequestToEntity(projectCreateRequest: ProjectCreateRequest): Project {
        return Project(
            0,
            projectCreateRequest.name,
            projectCreateRequest.field,
            projectCreateRequest.experience,
            LocalDate.parse(projectCreateRequest.deadline, DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            projectCreateRequest.description,
        )
    }

    fun createProject(projectCreateRequest: ProjectCreateRequest): ProjectResponse? {
        val savedProject = projectRepository.save(convertRequestToEntity(projectCreateRequest))
        return convertEntityToResponse(savedProject)
    }

    fun getAllProjects(size: Int, page: Int, sortBy: String, order: String): PagedModel<ProjectResponse>? {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.fromString(order), sortBy)
        return PagedModel(projectRepository.findAll(pageable).map(this::convertEntityToResponse))
    }


    fun getProjectById(id: Long): ProjectResponse? {
        val project: Project = projectRepository.findById(id).orElse(null)
            ?: throw DataNotFoundException("Project with ID: $id does not exist!")
        return convertEntityToResponse(project)
    }


    fun updateProjectById(id: Long, projectUpdateRequest: ProjectUpdateRequest): ProjectResponse? {
        val project: Project = projectRepository.findById(id).orElse(null)
            ?: throw DataNotFoundException("Project with ID: $id does not exist!")

        projectUpdateRequest.name?.also {
            project.name = projectUpdateRequest.name!!
        }

        projectUpdateRequest.field?.also {
            project.field = projectUpdateRequest.field!!
        }

        projectUpdateRequest.experience?.also {
            project.experience = projectUpdateRequest.experience!!
        }

        projectUpdateRequest.deadline?.also {
            project.deadline = LocalDate.parse(projectUpdateRequest.deadline!!, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        }

        projectUpdateRequest.description?.also {
            project.description = projectUpdateRequest.description!!
        }

        val updatedProject: Project = projectRepository.save(project)
        return convertEntityToResponse(updatedProject)
    }

    fun deleteProjectById(id: Long): String? {
        if(projectRepository.existsById(id)){
            projectRepository.deleteById(id)
            return "Project with id: $id has been deleted."
        } else{
            throw DataNotFoundException("Project with ID: $id does not exist!")
        }
    }

    fun deleteAllProjects(password: String): String? {
        if (password == pass) {
            val projectList: List<Project> = projectRepository.findAll()
            projectRepository.deleteAll(projectList)
            return "All projects have been deleted."
        } else {
            throw BadRequestException ("The given password is not valid.")
        }
    }
}
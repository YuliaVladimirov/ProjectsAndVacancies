package org.example.projects_and_vacancies.services

import org.example.projects_and_vacancies.dtos.*
import org.example.projects_and_vacancies.entities.Project
import org.example.projects_and_vacancies.entities.Vacancy
import org.example.projects_and_vacancies.exceptions.BadRequestException
import org.example.projects_and_vacancies.exceptions.DataNotFoundException
import org.example.projects_and_vacancies.repositories.ProjectRepository
import org.example.projects_and_vacancies.repositories.VacancyRepository
import org.springframework.stereotype.Service

@Service
class VacancyService(
    private var vacancyRepository: VacancyRepository,
    private var projectRepository: ProjectRepository,

    ) {

    private val pass: String = System.getenv("CLEAN_PASSWORD")

    fun convertEntityToResponse(vacancy: Vacancy): VacancyResponse {
        return VacancyResponse(
            vacancy.id,
            vacancy.name,
            vacancy.field,
            vacancy.experience,
            vacancy.country,
            vacancy.description
        )
    }

    fun convertCreateRequestToEntity(vacancyCreateRequest: VacancyCreateRequest, project: Project): Vacancy {
        return Vacancy(
            0,
            vacancyCreateRequest.name,
            vacancyCreateRequest.field,
            vacancyCreateRequest.experience,
            vacancyCreateRequest.country,
            vacancyCreateRequest.description,
            project
        )
    }

    fun createVacancy(id: Long, vacancyCreateRequest: VacancyCreateRequest): VacancyResponse? {
        val project: Project = projectRepository.findById(id).orElse(null)
            ?: throw DataNotFoundException("Project with ID: $id does not exist!")
        val vacancy: Vacancy = convertCreateRequestToEntity(vacancyCreateRequest, project)
        val savedVacancy = vacancyRepository.save(vacancy)
        return convertEntityToResponse(savedVacancy)
    }


    fun getAllVacancies(id: Long): List<VacancyResponse>? {
        val project: Project = projectRepository.findById(id).orElse(null)
            ?: throw DataNotFoundException("Project with ID: $id does not exist!")
        return project.vacancies.map(this::convertEntityToResponse)
    }

    fun getVacancyById(id: Long): VacancyResponse? {
        val vacancy: Vacancy = vacancyRepository.findById(id).orElse(null)
            ?: throw DataNotFoundException("Vacancy with ID: $id does not exist!")
        return convertEntityToResponse(vacancy)
    }

    fun updateVacancyById(id: Long, vacancyUpdateRequest: VacancyUpdateRequest): VacancyResponse? {
        val vacancy: Vacancy = vacancyRepository.findById(id).orElse(null)
            ?: throw DataNotFoundException("Vacancy with ID: $id does not exist!")

        vacancyUpdateRequest.name?.also {
            vacancy.name = vacancyUpdateRequest.name!!
        }

        vacancyUpdateRequest.field?.also {
            vacancy.field = vacancyUpdateRequest.field!!
        }

        vacancyUpdateRequest.experience?.also {
            vacancy.experience = vacancyUpdateRequest.experience!!
        }

        vacancyUpdateRequest.country?.also {
            vacancy.country = vacancyUpdateRequest.country!!
        }

        vacancyUpdateRequest.description?.also {
            vacancy.description = vacancyUpdateRequest.description!!
        }

        val updatedVacancy: Vacancy = vacancyRepository.save(vacancy)
        return convertEntityToResponse(updatedVacancy)
    }

    fun deleteVacancyById(id: Long): String? {
        val vacancy: Vacancy = vacancyRepository.findById(id).orElse(null)
            ?: throw DataNotFoundException("Vacancy with ID: $id does not exist!")
        vacancyRepository.delete(vacancy)
        return "Vacancy with id: $id has been deleted."
    }

    fun deleteAllVacancies(password: String): String? {
        if (password == pass) {
            val vacancyList: List<Vacancy> = vacancyRepository.findAll()
            vacancyRepository.deleteAll(vacancyList)
            return "All vacancies have been deleted."
        } else {
            throw BadRequestException ("The given password is not valid.")
        }
    }
}
package org.example.projects_and_vacancies.repositories

import org.example.projects_and_vacancies.entities.Vacancy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface VacancyRepository : JpaRepository<Vacancy, Long>, PagingAndSortingRepository<Vacancy, Long>


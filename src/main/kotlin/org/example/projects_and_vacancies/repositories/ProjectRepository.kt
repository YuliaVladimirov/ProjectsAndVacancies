package org.example.projects_and_vacancies.repositories

import org.example.projects_and_vacancies.entities.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository: JpaRepository<Project,Long>, PagingAndSortingRepository<Project,Long>




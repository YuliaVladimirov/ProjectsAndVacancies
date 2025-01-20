package org.example.projects_and_vacancies.entities

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "projects")
class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "field", nullable = false)
    var field: String,

    @Column(name = "experience", nullable = false)
    var experience: String,

    @Column(name = "deadline", nullable = false)
    var deadline: LocalDate,

    @Column(name = "description", nullable = false)
    var description: String,

    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], orphanRemoval = true)
    var vacancies: MutableList<Vacancy> = mutableListOf()

)
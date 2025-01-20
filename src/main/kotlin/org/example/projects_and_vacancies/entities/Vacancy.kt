package org.example.projects_and_vacancies.entities

import jakarta.persistence.*

@Entity
@Table(name = "vacancies")
class Vacancy(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "field", nullable = false)
    var field: String,

    @Column(name = "experience", nullable = false)
    var experience: String,

    @Column(name = "country", nullable = false)
    var country: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_id", nullable=false)
    var project: Project
)
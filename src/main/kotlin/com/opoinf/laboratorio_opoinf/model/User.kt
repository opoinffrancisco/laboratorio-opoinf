package com.opoinf.laboratorio_opoinf.model

import jakarta.persistence.*
import java.util.*

@Entity
data class AppUser(
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID DEFAULT uuid_generate_v4()")
    var id: UUID,
    var email: String,
    var password: String,
    var role: Role
)

enum class Role {
    USER, ADMIN
}
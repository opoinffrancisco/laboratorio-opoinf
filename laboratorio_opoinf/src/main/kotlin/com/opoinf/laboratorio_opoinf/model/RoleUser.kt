package com.opoinf.laboratorio_opoinf.model

import jakarta.persistence.*
import java.util.*

/*
* AUN NO SE ESTA USANDO
* */
@Entity
data class RoleUser(
    @Id
    //@GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID DEFAULT uuid_generate_v4()")
    var id: UUID,
    var name: String
)

package com.opoinf.laboratorio_opoinf.model

import java.time.Instant
import jakarta.persistence.*

@Entity
data class PasswordResetToken(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val token: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    val user: AppUser,

    val expirationDate: Instant
)
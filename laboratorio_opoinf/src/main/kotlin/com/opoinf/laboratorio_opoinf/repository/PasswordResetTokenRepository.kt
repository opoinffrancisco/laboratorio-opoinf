package com.opoinf.laboratorio_opoinf.repository

import com.opoinf.laboratorio_opoinf.model.PasswordResetToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PasswordResetTokenRepository : JpaRepository<PasswordResetToken, Long> {
    fun findByToken(token: String): PasswordResetToken?
}
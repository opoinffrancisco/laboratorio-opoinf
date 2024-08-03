package com.opoinf.laboratorio_opoinf.service

import com.opoinf.laboratorio_opoinf.model.AppUser
import com.opoinf.laboratorio_opoinf.model.PasswordResetToken
import com.opoinf.laboratorio_opoinf.repository.AppUserRepository
import com.opoinf.laboratorio_opoinf.repository.PasswordResetTokenRepository
import com.opoinf.laboratorio_opoinf.util.exception.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.security.core.context.SecurityContextHolder
import java.time.Instant
//import org.springframework.security.oauth2.jwt.Jwt
import java.util.*

@Service
class AppUserService(
    @Autowired private val AppUserRepository: AppUserRepository,
    private val encoder: PasswordEncoder,
    val emailService: EmailService,
    val passwordResetTokenRepository: PasswordResetTokenRepository
) {

    fun save(user: AppUser): AppUser? {
        val existingUser: AppUser? = AppUserRepository.findByEmail(user.email)

        return if (existingUser?.email == user.email) null else {
            val updated = user.copy(password = encoder.encode(user.password))
            AppUserRepository.save(updated)
            updated
        }
    }

    fun edit(uuid: UUID, user: AppUser): AppUser? {

        val existingUser: AppUser = findByUUID(uuid)
            ?: throw BadRequestException("No se pudo editar el usuario: Porque no fue encontrado por el ID.")

        if (uuid != this.authenticatedUserId()) {
            throw BadRequestException("No se pudo editar el usuario: No autorizado para editar este usuario.")
        }

        if (user.email != existingUser.email) {
            val existingEmailUser: AppUser? = AppUserRepository.findByEmail(user.email)
            if (existingEmailUser != null) {
                throw BadRequestException("No se pudo editar el usuario: El correo electronico ya existe.")
            }
        }

        val updatedUser = existingUser.copy(
            id = uuid,
            email = user.email,
            password = if (existingUser.password == user.password) existingUser.password else encoder.encode(user.password),
        )

        return AppUserRepository.save(updatedUser)
    }

    fun findByEmail(email: String): AppUser? = AppUserRepository.findByEmail(email)

    fun findByUUID(uuid: UUID): AppUser? = AppUserRepository.findById(uuid).orElse(null)

    fun findAll(): List<AppUser> = AppUserRepository.findAll()

    fun deleteByUUID(uuid: UUID): Boolean {
        return if (AppUserRepository.existsById(uuid)) {
            AppUserRepository.deleteById(uuid)
            true
        } else {
            false
        }
    }

    fun generatePasswordResetToken(email: String) {
        val user = AppUserRepository.findByEmail(email) ?: throw BadRequestException("Usuario no encontrado con el correo proporcionado")
        val token = UUID.randomUUID().toString()
        val expirationDate = Instant.now().plusSeconds(3600) // El token expira en 1 hora

        val passwordResetToken = PasswordResetToken(
            token = token,
            user = user,
            expirationDate = expirationDate
        )
        passwordResetTokenRepository.save(passwordResetToken)

        emailService.send(
            email,
            "SOPORTE APP MOVIL: Recuperación de contraseña",
            "Use el siguiente TOKEN para restablecer su contraseña: $token  dirijase a la app para continuar el cambio de contraseña. (En 1h se vence el token)."
        )
    }

    fun resetPassword(token: String, newPassword: String) {
        val passwordResetToken: PasswordResetToken = passwordResetTokenRepository.findByToken(token)
            ?: throw BadRequestException("Token inválido o expirado")

        if (passwordResetToken.expirationDate.isBefore(Instant.now())) {
            throw BadRequestException("Token expirado")
        }

        val user = passwordResetToken.user
        user.password = encoder.encode(newPassword)
        AppUserRepository.save(user)

        passwordResetTokenRepository.delete(passwordResetToken) // Eliminar el token después de usarlo
    }

    private fun authenticatedUserId(): UUID? {
        // Obtener el nombre de usuario del JWT (que se asume contiene el ID del usuario)
        val authentication = SecurityContextHolder.getContext().authentication
        val authenticatedUserEmail = authentication.name.toString()
        val authenticatedUserId: UUID? = AppUserRepository.findByEmail(authenticatedUserEmail)?.id
        return authenticatedUserId
    }
}
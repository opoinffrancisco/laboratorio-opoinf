package com.opoinf.laboratorio_opoinf.service

import com.opoinf.laboratorio_opoinf.model.AppUser
import com.opoinf.laboratorio_opoinf.repository.AppUserRepository
import com.opoinf.laboratorio_opoinf.util.exception.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.security.oauth2.jwt.Jwt
import java.util.*

@Service
class AppUserService(
    @Autowired private val AppUserRepository: AppUserRepository,
    private val encoder: PasswordEncoder
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
   //.toList()

    fun deleteByUUID(uuid: UUID): Boolean {
        return if (AppUserRepository.existsById(uuid)) {
            AppUserRepository.deleteById(uuid)
            true
        } else {
            false
        }
    }

    private fun authenticatedUserId(): UUID? {
        // Obtener el nombre de usuario del JWT (que se asume contiene el ID del usuario)
        val authentication = SecurityContextHolder.getContext().authentication
        val authenticatedUserEmail = authentication.name.toString()
        val authenticatedUserId: UUID? = AppUserRepository.findByEmail(authenticatedUserEmail)?.id
        return authenticatedUserId
    }
}
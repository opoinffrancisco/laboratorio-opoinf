package com.opoinf.laboratorio_opoinf.service

import com.opoinf.laboratorio_opoinf.model.AppUser
import com.opoinf.laboratorio_opoinf.repository.AppUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
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

    fun editar(uuid: UUID, user: AppUser): AppUser? {
        val existingUser: AppUser? = AppUserRepository.findByEmail(user.email)

        return if (existingUser?.email == user.email) null else {
            val updated = user.copy(password = encoder.encode(user.password))
            AppUserRepository.save(updated)
            updated
        }
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
}
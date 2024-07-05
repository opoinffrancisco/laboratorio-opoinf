package com.opoinf.laboratorio_opoinf.repository

import com.opoinf.laboratorio_opoinf.model.Role
import com.opoinf.laboratorio_opoinf.model.AppUser
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AppUserRepository(
    private val encoder: PasswordEncoder
) {

    private val users = mutableSetOf(
        AppUser(
            id = UUID.randomUUID(),
            email = "email-1@gmail.com",
            password = encoder.encode("pass1"),
            role = Role.USER,
        ),
        AppUser(
            id = UUID.randomUUID(),
            email = "email-2@gmail.com",
            password = encoder.encode("pass2"),
            role = Role.ADMIN,
        ),
        AppUser(
            id = UUID.randomUUID(),
            email = "email-3@gmail.com",
            password = encoder.encode("pass3"),
            role = Role.USER,
        ),
    )

    fun save(user: AppUser): Boolean {
        val updated = user.copy(password = encoder.encode(user.password))

        return users.add(updated)
    }

    fun findByEmail(email: String): AppUser? =
        users
            .firstOrNull { it.email == email }

    fun findAll(): Set<AppUser> =
        users

    fun findByUUID(uuid: UUID): AppUser? =
        users
            .firstOrNull { it.id == uuid }

    fun deleteByUUID(uuid: UUID): Boolean {
        val foundUser = findByUUID(uuid)

        return foundUser?.let {
            users.removeIf {
                it.id == uuid
            }
        } ?: false
    }
}
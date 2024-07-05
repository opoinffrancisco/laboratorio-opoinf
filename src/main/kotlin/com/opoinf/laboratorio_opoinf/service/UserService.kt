package com.opoinf.laboratorio_opoinf.service

import com.opoinf.laboratorio_opoinf.model.AppUser
import com.opoinf.laboratorio_opoinf.repository.AppUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AppUserService(@Autowired private val AppUserRepository: AppUserRepository) {

    fun save(user: AppUser): AppUser? {
        val existingUser  = AppUserRepository.findByEmail(user.email)

        return if (existingUser  == null) {
            AppUserRepository.save(user)
            user
        } else null
    }

    fun findByUUID(uuid: UUID): AppUser? =
        AppUserRepository.findByUUID(uuid)

    fun findAll(): List<AppUser> =
        AppUserRepository.findAll()
            .toList()

    fun deleteByUUID(uuid: UUID): Boolean =
        AppUserRepository.deleteByUUID(uuid)

}
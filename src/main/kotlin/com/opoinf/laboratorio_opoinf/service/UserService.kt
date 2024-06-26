package com.opoinf.laboratorio_opoinf.service

import com.opoinf.laboratorio_opoinf.model.AppUser
import com.opoinf.laboratorio_opoinf.repository.AppUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AppUserService(@Autowired private val AppUserRepository: AppUserRepository) {
    fun findAll(): List<AppUser> = AppUserRepository.findAll()
    fun findById(id: Long): AppUser? = AppUserRepository.findById(id).orElse(null)
    fun save(AppUser: AppUser): AppUser = AppUserRepository.save(AppUser)
    fun deleteById(id: Long) = AppUserRepository.deleteById(id)
}
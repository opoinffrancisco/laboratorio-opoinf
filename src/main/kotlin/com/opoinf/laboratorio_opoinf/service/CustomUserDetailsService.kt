package com.opoinf.laboratorio_opoinf.service

import com.opoinf.laboratorio_opoinf.repository.AppUserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.opoinf.laboratorio_opoinf.model.AppUser

@Service
class CustomUserDetailsService(
  private val userRepository: AppUserRepository
) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails =
    userRepository.findByEmail(username)
      ?.mapToUserDetails()
      ?: throw UsernameNotFoundException("Not found!")

  private fun ApplicationUser.mapToUserDetails(): UserDetails =
    User.builder()
      .username(this.email)
      .password(this.password)
      .roles(this.role.name)
      .build()
}
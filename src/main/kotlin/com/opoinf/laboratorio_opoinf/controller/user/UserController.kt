package com.opoinf.laboratorio_opoinf.controller.user

import com.opoinf.laboratorio_opoinf.model.Role
import com.opoinf.laboratorio_opoinf.model.AppUser
import com.opoinf.laboratorio_opoinf.service.AppUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/user")
class UserController(
  private val userService: AppUserService
) {

  @PostMapping
  fun create(@RequestBody userRequest: UserRequest): UserResponse =
    userService.save(userRequest.toModel())
      ?.toResponse()
      ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create user.")

  @GetMapping
  fun listAll(): List<UserResponse> =
    userService.findAll()
      .map { it.toResponse() }

  @GetMapping("/{uuid}")
  fun findByUUID(@PathVariable uuid: UUID): UserResponse =
    userService.findByUUID(uuid)
      ?.toResponse()
      ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")


  @DeleteMapping("/{uuid}")
  fun deleteByUUID(@PathVariable uuid: UUID): ResponseEntity<Boolean> {
    val success = userService.deleteByUUID(uuid)

    return if (success)
      ResponseEntity.noContent()
        .build()
    else
      throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
  }

  private fun AppUser.toResponse(): UserResponse =
    UserResponse(
      uuid = this.id,
      email = this.email,
    )

  private fun UserRequest.toModel(): AppUser =
    AppUser(
      id = UUID.randomUUID(),
      email = this.email,
      password = this.password,
      role = Role.USER,
    )
}
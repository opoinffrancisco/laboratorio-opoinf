package com.opoinf.laboratorio_opoinf.controller.user

import com.opoinf.laboratorio_opoinf.model.Role
import com.opoinf.laboratorio_opoinf.model.AppUser
import com.opoinf.laboratorio_opoinf.controller.user.UserRequest
import com.opoinf.laboratorio_opoinf.controller.user.UserResponse
import com.opoinf.laboratorio_opoinf.service.AppUserService
import com.opoinf.laboratorio_opoinf.util.exception.BadRequestException
import com.opoinf.laboratorio_opoinf.util.exception.ResourceNotFoundException
import com.opoinf.laboratorio_opoinf.util.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/user")
class UserController(
  private val userService: AppUserService
) {

  @GetMapping
  fun listAll(): List<UserResponse> {
    return userService.findAll().map { it.toResponse() }
  }

  @GetMapping("/{uuid}")
  fun findByUUID(@PathVariable uuid: UUID): UserResponse {
    val user = userService.findByUUID(uuid)
    return user?.toResponse() ?: throw ResourceNotFoundException("Usuario no encontrado.")
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun create(@Valid @RequestBody userRequest: UserRequest): UserResponse {
    val user = userService.save(userRequest.toModel())
    return user?.toResponse() ?: throw BadRequestException("No se pudo crear el usuario.")
  }

  @PutMapping("/{uuid}")
  fun updateUser(@PathVariable uuid: UUID, @Valid @RequestBody userRequest: UserRequest): UserResponse {
    val user = userService.edit(uuid, userRequest.toModel())
    return user?.toResponse() ?: throw BadRequestException("No se pudo editar el usuario.")
  }

  @DeleteMapping("/{uuid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteByUUID(@PathVariable uuid: UUID) {
    val success = userService.deleteByUUID(uuid)
    if (!success) {
      throw ResourceNotFoundException("Usuario no eliminado.")
    }
  }

  private fun AppUser.toResponse(): UserResponse =
    UserResponse(
      uuid = this.id,
      email = this.email,
      role = this.role
    )

  private fun UserRequest.toModel(): AppUser =
    AppUser(
      id = UUID.randomUUID(),
      email = this.email,
      password = this.password,
      role = Role.USER,
    )
}

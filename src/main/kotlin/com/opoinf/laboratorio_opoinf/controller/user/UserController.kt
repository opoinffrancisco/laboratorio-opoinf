package com.opoinf.laboratorio_opoinf.controller.user

import com.opoinf.laboratorio_opoinf.model.Role
import com.opoinf.laboratorio_opoinf.model.AppUser
import com.opoinf.laboratorio_opoinf.service.AppUserService
import com.opoinf.laboratorio_opoinf.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/user")
class UserController(
  private val userService: AppUserService
) {

  @GetMapping
  fun listAll(): ResponseEntity<ApiResponse<List<UserResponse>>> {
    val users = userService.findAll().map { it.toResponse() }
    return ResponseEntity.ok(ApiResponse(data = users))
  }

  @GetMapping("/{uuid}")
  fun findByUUID(@PathVariable uuid: UUID): ResponseEntity<ApiResponse<UserResponse>> {
    val user = userService.findByUUID(uuid)
    return if (user != null) {
      ResponseEntity.ok(ApiResponse(data = user.toResponse()))
    } else {
      ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse(message = "Usuario no encontrado."))
    }
  }

  @PostMapping
  fun create(@RequestBody userRequest: UserRequest): ResponseEntity<ApiResponse<UserResponse>> {
    val user = userService.save(userRequest.toModel())
    return if (user != null) {
      ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse(data = user.toResponse()))
    } else {
      ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse(message = "No se pudo crear el usuario."))
    }
  }

  @PutMapping("/{uuid}")
  fun updateUser(@PathVariable uuid: UUID, @RequestBody updatedUser: AppUser): ResponseEntity<ApiResponse<UserResponse>> {
    val user = userService.editar(uuid, updatedUser)
    return if (user != null) {
      ResponseEntity.ok(ApiResponse(data = user.toResponse()))
    } else {
      ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse(message = "No se pudo editar el usuario."))
    }
  }

  @DeleteMapping("/{uuid}")
  fun deleteByUUID(@PathVariable uuid: UUID): ResponseEntity<ApiResponse<Void>> {
    val success = userService.deleteByUUID(uuid)
    return if (success) {
      ResponseEntity.noContent().build()
    } else {
      ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse(message = "Usuario no eliminado."))
    }
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

package com.opoinf.laboratorio_opoinf.controller.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AuthenticationRequest(
  @field:NotBlank(message = "El email no puede estar vacío")
  @field:Email(message = "El correo electrónico no es válido.")
  val email: String,

  @field:NotBlank(message = "La contraseña no puede estar vacía")
  @field:Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
  val password: String,
)
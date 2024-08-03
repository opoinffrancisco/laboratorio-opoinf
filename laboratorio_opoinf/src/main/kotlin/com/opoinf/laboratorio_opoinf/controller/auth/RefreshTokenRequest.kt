package com.opoinf.laboratorio_opoinf.controller.auth

import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest(
  @field:NotBlank(message = "El TOKEN no puede estar vacío")
  val token: String
)
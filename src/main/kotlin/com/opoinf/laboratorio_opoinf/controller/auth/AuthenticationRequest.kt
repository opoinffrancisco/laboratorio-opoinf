package com.opoinf.laboratorio_opoinf.controller.auth

data class AuthenticationRequest(
  val email: String,
  val password: String,
)
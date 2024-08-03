package com.opoinf.laboratorio_opoinf.controller.auth

data class AuthenticationResponse(
  val accessToken: String,
  val refreshToken: String,
)
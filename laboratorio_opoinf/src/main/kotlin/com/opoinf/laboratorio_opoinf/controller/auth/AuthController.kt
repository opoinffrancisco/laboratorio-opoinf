package com.opoinf.laboratorio_opoinf.controller.auth

import com.opoinf.laboratorio_opoinf.service.AppUserService
import com.opoinf.laboratorio_opoinf.util.response.ApiResponse
import com.opoinf.laboratorio_opoinf.service.AuthenticationService
import com.opoinf.laboratorio_opoinf.util.exception.ResourceNotFoundException
import com.opoinf.laboratorio_opoinf.controller.auth.ForgotPasswordResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
  private val authenticationService: AuthenticationService,
  val userService: AppUserService
) {

  @PostMapping
  fun authenticate(
    @Valid @RequestBody authRequest: AuthenticationRequest
  ): AuthenticationResponse =
    authenticationService.authentication(authRequest)

  @PostMapping("/refresh")
  fun refreshAccessToken(
    @Valid @RequestBody request: RefreshTokenRequest
  ): ResponseEntity<ApiResponse<TokenResponse>> {
    val tokenResponse = authenticationService.refreshAccessToken(request.token)
    return if (tokenResponse != null) {
      ResponseEntity.ok(ApiResponse(data = tokenResponse.mapToTokenResponse()))
    } else {
      ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse(message = "El REFRESH TOKEN es Invalido.", status = 403))
    }
  }

  @PostMapping("/forgot-password")
  fun forgotPassword(@RequestBody request: ForgotPasswordRequest): ForgotPasswordResponse {
    userService.generatePasswordResetToken(request.email)
    return this.toForgotPasswordResponse("TOKEN enviado su correo electronico: ${request.email}", 200)
  }

  @PostMapping("/reset-password")
  fun resetPassword(@RequestBody request: ResetPasswordRequest): ForgotPasswordResponse {
    userService.resetPassword(request.token, request.new_password)
    return this.toForgotPasswordResponse("Contraseña cambiada.", 200)
  }

  private fun String.mapToTokenResponse(): TokenResponse =
    TokenResponse(
      token = this
    )

  private fun toForgotPasswordResponse(message: String, status: Int): ForgotPasswordResponse =
    ForgotPasswordResponse(
      message = message,
      status = status
    )
}


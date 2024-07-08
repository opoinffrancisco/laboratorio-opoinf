package com.opoinf.laboratorio_opoinf.controller.auth

import com.opoinf.laboratorio_opoinf.util.response.ApiResponse
import com.opoinf.laboratorio_opoinf.service.AuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(
  private val authenticationService: AuthenticationService
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

  private fun String.mapToTokenResponse(): TokenResponse =
    TokenResponse(
      token = this
    )
}


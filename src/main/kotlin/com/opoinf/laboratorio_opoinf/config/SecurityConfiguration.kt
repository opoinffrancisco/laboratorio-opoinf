package com.opoinf.laboratorio_opoinf.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
  private val authenticationProvider: AuthenticationProvider
) {

  @Bean
  fun securityFilterChain(
    http: HttpSecurity,
    jwtAuthenticationFilter: JwtAuthenticationFilter
  ): DefaultSecurityFilterChain {
    http
      // Deshabilitar CSRF ya que estamos usando tokens JWT que no son vulnerables a CSRF
      .csrf { it.disable() }
      .authorizeHttpRequests {
        it
          // Permitir acceso sin autenticación a las rutas especificadas
          .requestMatchers("/api/auth", "api/auth/refresh", "/error")
          .permitAll()
          // Permitir POST sin autenticación a "/api/user" (esto puede ser para registro de usuarios)
          .requestMatchers(HttpMethod.POST, "/api/user", "/api/auth/forgot-password", "/api/auth/reset-password")
          .permitAll()
          // Requerir rol ADMIN para cualquier solicitud a "/api/user**" excepto POST
          .requestMatchers("/api/user**")
          .hasRole("ADMIN")
          // Requerir autenticación para cualquier otra solicitud
          .anyRequest().fullyAuthenticated()
      }
      // Configurar sesión como stateless ya que estamos usando JWT
      .sessionManagement {
        it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      }
      // Configurar proveedor de autenticación
      .authenticationProvider(authenticationProvider)
      // Añadir filtro de autenticación JWT antes del filtro de autenticación por nombre de usuario y contraseña
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

    // Construir y devolver la cadena de filtros de seguridad
    return http.build()
  }
}
package com.opoinf.laboratorio_opoinf.controller.user


import com.opoinf.laboratorio_opoinf.model.Role
import java.util.*

data class UserResponse(
  val uuid: UUID,
  val email: String,
  val role: Role,
)
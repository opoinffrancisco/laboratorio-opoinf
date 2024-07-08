package com.opoinf.laboratorio_opoinf.response

data class ApiResponse<T>(
  val data: T? = null,
  val message: String? = null
)

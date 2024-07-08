package com.opoinf.laboratorio_opoinf.util.response

data class ApiResponse<T>(
  val data: T? = null,
  val message: String? = null,
  val status: Int? = 200
)
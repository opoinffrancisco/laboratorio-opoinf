package com.opoinf.laboratorio_opoinf.controller.auth

data class ResetPasswordRequest(
    val token: String,
    val new_password: String
)
package com.opoinf.laboratorio_opoinf.util.exception

import com.opoinf.laboratorio_opoinf.util.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Map<String, String?>>> {
        val errors = ex.bindingResult.allErrors.associate {
            val fieldName = (it as FieldError).field
            val errorMessage = it.defaultMessage
            fieldName to errorMessage
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse(message = "Errores de validación", status = 400, data = errors))
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<ApiResponse<Void>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse(message = ex.message, status = 404))
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ApiResponse<Void>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse(message = ex.message, status = 400))
    }
}

package com.opoinf.laboratorio_opoinf.controller

import com.opoinf.laboratorio_opoinf.model.AppUser
import com.opoinf.laboratorio_opoinf.service.AppUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/appusers")
class AppUserController(@Autowired private val AppUserService: AppUserService) {

    @GetMapping
    fun getAllAppUsers(): List<AppUser> = AppUserService.findAll()

    @GetMapping("/{id}")
    fun getAppUserById(@PathVariable id: UUID): ResponseEntity<AppUser> {
        val AppUser = AppUserService.findByUUID(id)
        return if (AppUser != null) {
            ResponseEntity.ok(AppUser)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createAppUser(@RequestBody AppUser: AppUser): AppUser? = AppUserService.save(AppUser)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: UUID, @RequestBody updatedUser: AppUser): ResponseEntity<AppUser> {
        val existingUser = AppUserService.findByUUID(id)
        return if (existingUser != null) {
            // Actualiza los campos del usuario existente con los datos recibidos
            existingUser.apply {
                email = updatedUser.email
                // Puedes agregar más campos aquí si es necesario
            }
            // Guarda el usuario actualizado en la base de datos
            ResponseEntity.ok(AppUserService.save(existingUser))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteAppUserById(@PathVariable id: UUID): ResponseEntity<Void> {
        AppUserService.deleteByUUID(id)
        return ResponseEntity.noContent().build()
    }
}
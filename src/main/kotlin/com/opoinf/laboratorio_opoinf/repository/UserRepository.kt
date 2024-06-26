package com.opoinf.laboratorio_opoinf.repository

import com.opoinf.laboratorio_opoinf.model.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepository : JpaRepository<AppUser, Long>
package com.example.securitypoc.repositories.security

import com.example.securitypoc.entities.security.Tenant
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TenantRepository: JpaRepository<Tenant, UUID> {


    fun findByNameIgnoreCase(name: String): Tenant?


}
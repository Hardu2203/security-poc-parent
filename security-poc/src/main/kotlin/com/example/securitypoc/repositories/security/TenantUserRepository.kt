package com.example.securitypoc.repositories.security

import com.example.securitypoc.entities.security.TenantUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TenantUserRepository: JpaRepository<TenantUser, UUID> {
}
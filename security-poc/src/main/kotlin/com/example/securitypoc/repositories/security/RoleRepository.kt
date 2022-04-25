package com.example.securitypoc.repositories.security

import com.example.securitypoc.entities.security.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RoleRepository: JpaRepository<Role, UUID> {
}
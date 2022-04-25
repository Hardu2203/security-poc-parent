package com.example.securitypoc.repositories.security

import com.example.securitypoc.entities.security.Authority
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuthorityRepository: JpaRepository<Authority, UUID> {
}
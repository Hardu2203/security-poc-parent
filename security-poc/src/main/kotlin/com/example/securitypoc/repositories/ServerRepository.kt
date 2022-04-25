package com.example.securitypoc.repositories

import com.example.securitypoc.entities.Server
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ServerRepository: JpaRepository<Server, UUID> {
}
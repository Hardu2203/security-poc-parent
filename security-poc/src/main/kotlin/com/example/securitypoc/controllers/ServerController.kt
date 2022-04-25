package com.example.securitypoc.controllers

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/servers")
class ServerController() {

    @PostMapping
    fun createServer() : String {
        println(SecurityContextHolder.getContext().authentication.name)
        println(SecurityContextHolder.getContext().authentication.authorities)
        return "created server"
    }

    @GetMapping
    fun getAllServers(auth: Authentication): String {
        println(SecurityContextHolder.getContext().authentication.name)
        println(SecurityContextHolder.getContext().authentication.authorities)
      return "server fetched"
    }


    @PutMapping
    fun updateServer() = "updated server"

    @DeleteMapping
    fun deleteServer() = "delete server"

//    fun mapDtoToNode(serverDto: ServerDto): Server_ {
//        return Server_.SERVER.withProperties(
//            Server_.SERVER.NAME, Cypher.literalOf<String>(serverDto.name),
//            Server_.SERVER.IP, Cypher.literalOf<String>(serverDto.ip),
//            Server_.SERVER.MAKE, Cypher.literalOf<String>(serverDto.make),
//            Server_.SERVER.RAM, Cypher.literalOf<Long>(serverDto.ram),
//            Server_.SERVER.UNIT, Cypher.literalOf<String>(serverDto.unit),
//            Server_.SERVER.CPU_COUNT, Cypher.literalOf<Int>(serverDto.cpuCount),
//            Server_.SERVER.OS_PRODUCT, Cypher.literalOf<String>(serverDto.osProduct),
//            Server_.SERVER.OS_VERSION, Cypher.literalOf<String>(serverDto.osVersion)
//        )
//    }

}


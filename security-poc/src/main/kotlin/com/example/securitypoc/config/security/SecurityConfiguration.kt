package com.example.securitypoc.config.security

import com.example.securitypoc.repositories.security.RoleRepository
import com.example.securitypoc.repositories.security.TenantRepository
import com.example.securitypoc.repositories.security.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration {

    fun tenantHeaderAuthFilter(
        userRepository: UserRepository,
        roleRepository: RoleRepository,
        tenantRepository: TenantRepository
    ): TenantHeaderAuthFilter? {
        //        filter.setAuthenticationManager(authenticationManager);
        return TenantHeaderAuthFilter(
            AntPathRequestMatcher("/api/**"),
            userRepository,
            roleRepository,
            tenantRepository
        )
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        access: AccessRuleAuthorizationManager,
        userRepository: UserRepository,
        roleRepository: RoleRepository,
        tenantRepository: TenantRepository
    ): SecurityFilterChain? {

        http.authorizeHttpRequests { authz ->
            authz.antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/**").access(access)
        }
            .addFilterAfter(
                tenantHeaderAuthFilter(userRepository, roleRepository, tenantRepository),
                BearerTokenAuthenticationFilter::class.java
            )
            .csrf().disable()
            .cors(Customizer.withDefaults())
            .oauth2ResourceServer { obj: OAuth2ResourceServerConfigurer<HttpSecurity?> -> obj.jwt() }
        http.headers().frameOptions().sameOrigin()
        return http.build()
    }

//    @Bean
//    fun jwtAuthenticationConverter(): JwtAuthenticationConverter? {
//        val authoritiesConverter = JwtGrantedAuthoritiesConverter()
//        authoritiesConverter.setAuthorityPrefix("")
//        val authenticationConverter = JwtAuthenticationConverter()
//        authenticationConverter.setJwtGrantedAuthoritiesConverter { jwt: Jwt ->
//            if ("danny" != jwt.subject) {
//                return@setJwtGrantedAuthoritiesConverter authoritiesConverter.convert(jwt)
//            }
//            val authorities =
//                AuthorityUtils.authorityListToSet(authoritiesConverter.convert(jwt))
//            if (authorities.contains("flights:write")) {
//                authorities.add("flights:approve")
//            }
//            if (authorities.contains("flights:read")) {
//                authorities.add("flights:all")
//            }
//            AuthorityUtils.createAuthorityList(*authorities.toTypedArray())
//        }
//        return authenticationConverter
//    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        config.addAllowedOrigin("http://127.0.0.1:8000")
        config.allowCredentials = true
        source.registerCorsConfiguration("/**", config)
        return source
    }


}
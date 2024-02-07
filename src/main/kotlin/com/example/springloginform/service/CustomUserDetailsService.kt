package com.example.springloginform.service

import com.example.springloginform.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.example.springloginform.model.User
//기존 타입의 User를 이 클래스에서 다른 이름으로 사용하기 위해 씀
@Service
class CustomUserDetailsService (
    private val userRepository : UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByEmail(username)
            ?.mapToUserDetails()
            ?:throw UsernameNotFoundException("Not Found")


    private fun ApplicationUser.mapToUserDetails() : UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.role.name)
            .build()
}
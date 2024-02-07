package com.example.springloginform.controller.auth

import javax.management.monitor.StringMonitor

data class AuthenticationRequest (
    val email : String,
    val password: String
)
package br.pucpr.authserver.customers.requests

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CustomerRequest(
    @NotBlank
    val name: String?,

    @NotBlank @Email
    val email: String?
)
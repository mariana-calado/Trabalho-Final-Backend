package br.pucpr.authserver.categories.requests

import jakarta.validation.constraints.NotBlank

data class CategoryRequest(
    @NotBlank
    val name: String?
)

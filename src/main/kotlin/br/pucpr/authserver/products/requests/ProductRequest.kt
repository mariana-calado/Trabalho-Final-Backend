package br.pucpr.authserver.products.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class ProductRequest(
    @NotBlank
    val name: String?,

    @NotNull @Positive
    val price: BigDecimal?,

    @NotNull
    val categoryId: Long?
)

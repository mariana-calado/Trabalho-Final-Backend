package br.pucpr.authserver.orders.requests

import jakarta.validation.constraints.NotNull

data class OrderRequest(
    @NotNull
    val customerId: Long?
)
package br.pucpr.authserver.orders.responses

import br.pucpr.authserver.customers.responses.CustomerResponse
import br.pucpr.authserver.orders.Order
import br.pucpr.authserver.products.responses.ProductResponse

data class OrderResponse(
    val id: Long,
    val customer: CustomerResponse,
    val products: List<ProductResponse>,
) {
    constructor(order: Order) : this(
        order.id!!,
        CustomerResponse(order.customer),
        order.products.sortedBy { it.name }.map { ProductResponse(it) }
    )
}
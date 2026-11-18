package br.pucpr.authserver.customers.responses

import br.pucpr.authserver.customers.Customer

data class CustomerResponse(
    val id: Long,
    val name: String,
    val email: String,
) {
    constructor(customer: Customer) : this(customer.id!!, customer.name, customer.email)
}
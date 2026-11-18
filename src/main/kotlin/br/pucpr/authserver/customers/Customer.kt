package br.pucpr.authserver.customers

import br.pucpr.authserver.orders.Order
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Customer(
    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(unique = true, nullable = false)
    var email: String,

    @OneToMany(mappedBy = "customer")
    var orders: MutableList<Order> = mutableListOf()
)
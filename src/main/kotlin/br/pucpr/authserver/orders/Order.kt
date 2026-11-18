package br.pucpr.authserver.orders

import br.pucpr.authserver.customers.Customer
import br.pucpr.authserver.products.Product
import jakarta.persistence.*

@Entity
@Table(name = "OrderTable")
class Order(
    @Id @GeneratedValue
    var id: Long? = null,

    @ManyToOne(optional = false)
    var customer: Customer,

    @ManyToMany
    @JoinTable(
        name = "OrderProduct",
        joinColumns = [JoinColumn(name = "idOrder")],
        inverseJoinColumns = [JoinColumn(name = "idProduct")]
    )
    var products: MutableSet<Product> = mutableSetOf()
)
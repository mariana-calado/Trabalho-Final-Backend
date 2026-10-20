package br.pucpr.authserver.categories

import br.pucpr.authserver.products.Product
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Category(
    @Id @GeneratedValue
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var name: String,

    @OneToMany(mappedBy = "category")
    var products: MutableList<Product> = mutableListOf()
)

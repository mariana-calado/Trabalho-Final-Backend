package br.pucpr.authserver.products.responses

import br.pucpr.authserver.categories.responses.CategoryResponse
import br.pucpr.authserver.products.Product
import java.math.BigDecimal

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: BigDecimal,
    val category: CategoryResponse,
) {
    constructor(product: Product) : this(
        product.id!!,
        product.name,
        product.price,
        CategoryResponse(product.category)
    )
}

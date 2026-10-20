package br.pucpr.authserver.products

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun existsByCategoryId(categoryId: Long): Boolean

    @Query(
        """
            select p from Product p
            where (:categoryId is null or p.category.id = :categoryId)
              and (:minPrice is null or p.price >= :minPrice)
              and (:maxPrice is null or p.price <= :maxPrice)
        """
    )
    fun search(
        categoryId: Long?,
        minPrice: BigDecimal?,
        maxPrice: BigDecimal?,
        sort: Sort
    ): List<Product>
}

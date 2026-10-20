package br.pucpr.authserver.products

import br.pucpr.authserver.products.requests.ProductRequest
import br.pucpr.authserver.products.responses.ProductResponse
import br.pucpr.authserver.users.SortDir
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/products")
class ProductController(val service: ProductService) {
    @GetMapping
    fun list(
        @RequestParam categoryId: Long? = null,
        @RequestParam minPrice: BigDecimal? = null,
        @RequestParam maxPrice: BigDecimal? = null,
        @RequestParam sortBy: String? = null,
        @RequestParam sortDir: String? = null
    ) = service.search(
        categoryId,
        minPrice,
        maxPrice,
        sortBy ?: "name",
        SortDir.find(sortDir ?: "ASC")
    )
        .map { ProductResponse(it) }
        .let { ResponseEntity.ok(it) }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ) = service.findById(id)
        .let { ProductResponse(it) }
        .let { ResponseEntity.ok(it) }

    @SecurityRequirement(name = "jwt-auth")
    @PostMapping
    fun insert(
        @Valid @RequestBody product: ProductRequest
    ) = service.insert(product.name!!, product.price!!, product.categoryId!!)
        .let { ProductResponse(it) }
        .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    @SecurityRequirement(name = "jwt-auth")
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody product: ProductRequest
    ) = service.update(id, product.name!!, product.price!!, product.categoryId!!)
        .let { ProductResponse(it) }
        .let { ResponseEntity.ok(it) }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt-auth")
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ) = service.delete(id)
}

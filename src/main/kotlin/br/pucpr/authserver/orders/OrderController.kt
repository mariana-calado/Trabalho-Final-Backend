package br.pucpr.authserver.orders

import br.pucpr.authserver.orders.requests.OrderRequest
import br.pucpr.authserver.orders.responses.OrderResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(val service: OrderService) {
    @GetMapping
    fun list() = service.findAll()
        .map { OrderResponse(it) }
        .let { ResponseEntity.ok(it) }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ) = service.findById(id)
        .let { OrderResponse(it) }
        .let { ResponseEntity.ok(it) }

    @SecurityRequirement(name = "jwt-auth")
    @PostMapping
    fun insert(
        @Valid @RequestBody order: OrderRequest
    ) = service.insert(order.customerId!!)
        .let { OrderResponse(it) }
        .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    @SecurityRequirement(name = "jwt-auth")
    @PutMapping("/{id}/products/{productId}")
    fun addProduct(
        @PathVariable id: Long,
        @PathVariable productId: Long
    ): ResponseEntity<Void> = service.addProduct(id, productId)
        .let {
            if (it) ResponseEntity.ok().build()
            else ResponseEntity.noContent().build()
        }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt-auth")
    @DeleteMapping("/{id}/products/{productId}")
    fun removeProduct(
        @PathVariable id: Long,
        @PathVariable productId: Long
    ) = service.removeProduct(id, productId)
}
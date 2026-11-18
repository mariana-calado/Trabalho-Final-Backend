package br.pucpr.authserver.customers

import br.pucpr.authserver.customers.requests.CustomerRequest
import br.pucpr.authserver.customers.responses.CustomerResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(val service: CustomerService) {
    @GetMapping
    fun list() = service.findAll()
        .map { CustomerResponse(it) }
        .let { ResponseEntity.ok(it) }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ) = service.findById(id)
        .let { CustomerResponse(it) }
        .let { ResponseEntity.ok(it) }

    @SecurityRequirement(name = "jwt-auth")
    @PostMapping
    fun insert(
        @Valid @RequestBody customer: CustomerRequest
    ) = service.insert(customer.name!!, customer.email!!)
        .let { CustomerResponse(it) }
        .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
}
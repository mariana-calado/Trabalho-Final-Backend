package br.pucpr.authserver.categories

import br.pucpr.authserver.categories.requests.CategoryRequest
import br.pucpr.authserver.categories.responses.CategoryResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categories")
class CategoryController(val service: CategoryService) {
    @GetMapping
    fun list() = service.findAll()
        .map { CategoryResponse(it) }
        .let { ResponseEntity.ok(it) }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ) = service.findById(id)
        .let { CategoryResponse(it) }
        .let { ResponseEntity.ok(it) }

    @SecurityRequirement(name = "jwt-auth")
    @PostMapping
    fun insert(
        @Valid @RequestBody category: CategoryRequest
    ) = service.insert(category.name!!)
        .let { CategoryResponse(it) }
        .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    @SecurityRequirement(name = "jwt-auth")
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody category: CategoryRequest
    ) = service.update(id, category.name!!)
        .let { CategoryResponse(it) }
        .let { ResponseEntity.ok(it) }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt-auth")
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ) = service.delete(id)
}

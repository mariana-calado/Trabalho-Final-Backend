package br.pucpr.authserver.products

import br.pucpr.authserver.categories.CategoryRepository
import br.pucpr.authserver.exception.NotFoundException
import br.pucpr.authserver.exceptions.BadRequestException
import br.pucpr.authserver.users.SortDir
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ProductService(
    val repository: ProductRepository,
    val categoryRepository: CategoryRepository
) {
    fun insert(name: String, price: BigDecimal, categoryId: Long): Product {
        val category = categoryRepository.findByIdOrNull(categoryId)
            ?: throw BadRequestException("Category $categoryId not found")
        return repository.save(Product(name = name, price = price, category = category))
            .also { log.info("Product ${it.id} created successfully") }
    }

    fun search(
        categoryId: Long?,
        minPrice: BigDecimal?,
        maxPrice: BigDecimal?,
        sortBy: String,
        dir: SortDir
    ): List<Product> {
        if (sortBy !in SORT_FIELDS) {
            throw BadRequestException("Invalid sortBy. Use one of: ${SORT_FIELDS.joinToString()}")
        }
        val sort = Sort.by(sortBy).let { if (dir == SortDir.ASC) it.ascending() else it.descending() }
        return repository.search(categoryId, minPrice, maxPrice, sort)
    }

    fun findById(id: Long) = repository.findByIdOrNull(id) ?: throw NotFoundException(id)

    fun update(id: Long, name: String, price: BigDecimal, categoryId: Long): Product {
        val product = findById(id)
        val category = categoryRepository.findByIdOrNull(categoryId)
            ?: throw BadRequestException("Category $categoryId not found")
        product.name = name
        product.price = price
        product.category = category
        return repository.save(product)
            .also { log.info("Product $id updated successfully") }
    }

    fun delete(id: Long) {
        repository.delete(findById(id))
        log.info("Product $id deleted successfully")
    }

    companion object {
        val log = LoggerFactory.getLogger(ProductService::class.java)
        val SORT_FIELDS = listOf("name", "price")
    }
}

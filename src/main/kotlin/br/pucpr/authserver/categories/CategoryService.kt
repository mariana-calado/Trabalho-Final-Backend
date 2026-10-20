package br.pucpr.authserver.categories

import br.pucpr.authserver.exception.NotFoundException
import br.pucpr.authserver.exceptions.BadRequestException
import br.pucpr.authserver.products.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CategoryService(
    val repository: CategoryRepository,
    val productRepository: ProductRepository
) {
    fun insert(name: String): Category {
        if (repository.findByName(name) != null) {
            throw BadRequestException("Category $name already exists.")
        }
        return repository.save(Category(name = name))
            .also { log.info("Category ${it.id} created successfully") }
    }

    fun findAll() = repository.findAll(Sort.by("name").ascending())

    fun findById(id: Long) = repository.findByIdOrNull(id) ?: throw NotFoundException(id)

    fun update(id: Long, name: String): Category {
        val category = findById(id)
        val sameName = repository.findByName(name)
        if (sameName != null && sameName.id != id) {
            throw BadRequestException("Category $name already exists.")
        }
        category.name = name
        return repository.save(category)
            .also { log.info("Category $id updated successfully") }
    }

    fun delete(id: Long) {
        val category = findById(id)
        if (productRepository.existsByCategoryId(id)) {
            throw BadRequestException("Cannot delete a category that has products")
        }
        repository.delete(category)
        log.info("Category $id deleted successfully")
    }

    companion object {
        val log = LoggerFactory.getLogger(CategoryService::class.java)
    }
}

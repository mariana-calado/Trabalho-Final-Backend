package br.pucpr.authserver.orders

import br.pucpr.authserver.customers.CustomerRepository
import br.pucpr.authserver.exception.NotFoundException
import br.pucpr.authserver.exceptions.BadRequestException
import br.pucpr.authserver.products.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    val repository: OrderRepository,
    val customerRepository: CustomerRepository,
    val productRepository: ProductRepository
) {
    fun insert(customerId: Long): Order {
        val customer = customerRepository.findByIdOrNull(customerId)
            ?: throw NotFoundException(customerId)
        return repository.save(Order(customer = customer))
            .also { log.info("Order ${it.id} created successfully") }
    }

    fun findAll() = repository.findAll(Sort.by("id").ascending())

    fun findById(id: Long) = repository.findByIdOrNull(id) ?: throw NotFoundException(id)

    @Transactional
    fun addProduct(id: Long, productId: Long): Boolean {
        val order = findById(id)
        val product = productRepository.findByIdOrNull(productId)
            ?: throw NotFoundException(productId)
        if (order.products.any { it.id == productId }) return false

        order.products.add(product)
        repository.save(order)
        log.info("Product $productId added to order $id")
        return true
    }

    @Transactional
    fun removeProduct(id: Long, productId: Long) {
        val order = findById(id)
        if (!productRepository.existsById(productId)) {
            throw NotFoundException(productId)
        }
        val product = order.products.firstOrNull { it.id == productId }
            ?: throw BadRequestException("Product $productId is not in order $id.")

        order.products.remove(product)
        repository.save(order)
        log.info("Product $productId removed from order $id")
    }

    companion object {
        val log = LoggerFactory.getLogger(OrderService::class.java)
    }
}
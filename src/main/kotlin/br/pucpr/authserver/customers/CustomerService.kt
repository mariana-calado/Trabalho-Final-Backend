package br.pucpr.authserver.customers

import br.pucpr.authserver.exception.NotFoundException
import br.pucpr.authserver.exceptions.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CustomerService(
    val repository: CustomerRepository
) {
    fun insert(name: String, email: String): Customer {
        if (repository.findByEmail(email) != null) {
            throw BadRequestException("Customer with email $email already exists.")
        }
        return repository.save(Customer(name = name, email = email))
            .also { log.info("Customer ${it.id} created successfully") }
    }

    fun findAll() = repository.findAll(Sort.by("name").ascending())

    fun findById(id: Long) = repository.findByIdOrNull(id) ?: throw NotFoundException(id)

    companion object {
        val log = LoggerFactory.getLogger(CustomerService::class.java)
    }
}
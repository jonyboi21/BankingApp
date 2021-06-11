package banking.fullstack.app.customer;

import banking.fullstack.app.customer.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByEmail(String Email);

}


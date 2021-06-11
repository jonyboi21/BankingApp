package service;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import repository.CustomerRepository;

public class CustomerService{
    @Autowired
    private CustomerRepository customerRepository;

    private void createCustomer(Customer customer){
        Boolean userExists = customerRepository
                .findByEmail(customer.getCustomerEmail())
                .isPresent();
        if(userExists){
            throw new IllegalStateException("email already taken");
        }
        customerRepository.save(customer);
    }


}

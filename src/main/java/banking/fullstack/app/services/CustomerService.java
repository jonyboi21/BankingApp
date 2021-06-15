package banking.fullstack.app.services;

//import com.example.bankingapi.account.AccountService;
import banking.fullstack.app.models.Customer;
import banking.fullstack.app.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;


    public Customer createCustomer(Customer customer){
        Boolean userExists = customerRepository
                .findByEmail(customer.getEmail())
                .isPresent();
        if(userExists){
            throw new IllegalStateException("email already taken");
        }else
            return customerRepository.save(customer);
    }
    public List<Customer> getAllCustomers(){
        List<Customer> listOfCustomers = new ArrayList<Customer>();
        customerRepository
                .findAll()
                .forEach(listOfCustomers::add);
        return listOfCustomers;
    }

    private Optional<Customer> getCustomerByEmail(Long id){
        return  customerRepository.findById(id);
    }

    public void updateCustomer(Customer customer, Long id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        customer.setId(id);
        customerRepository.save(customer);

    }

    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }

    public Optional<Customer> getCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer;
    }

    public boolean customerCheck(Long customerId){
        Customer customer = customerRepository.findById(customerId).orElse(null);
        return customer != null;
    }
}
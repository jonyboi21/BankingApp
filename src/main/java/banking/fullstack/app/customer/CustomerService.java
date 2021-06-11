package banking.fullstack.app.customer;

//import com.example.bankingapi.account.AccountService;
import banking.fullstack.app.customer.Customer;
import banking.fullstack.app.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;
    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    public void createCustomer(Customer customer){
        Boolean userExists = customerRepository
                .findByEmail(customer.getCustomerEmail())
                .isPresent();
        if(userExists){
            throw new IllegalStateException("email already taken");
        }
        customerRepository.save(customer);
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
        customer.setCustomerId(id);
        customerRepository.save(customer);

    }

    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return customerRepository.findByEmail(email)
                .orElseThrow();
    }

}
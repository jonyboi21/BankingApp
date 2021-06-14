package banking.fullstack.app.customer;

//import com.example.bankingapi.account.AccountService;
import banking.fullstack.app.customer.Customer;
import banking.fullstack.app.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    @Autowired
    private CustomerRepository customerRepository;


    public void createCustomer(Customer customer){
        Boolean userExists = customerRepository
                .findByEmail(customer.getEmail())
                .isPresent();
        if(userExists){
            throw new IllegalStateException("email already taken");
        }else
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
        customer.setId(id);
        customerRepository.save(customer);

    }

    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }


    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).get();
    }




}
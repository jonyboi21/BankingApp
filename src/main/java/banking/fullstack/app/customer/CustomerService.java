package banking.fullstack.app.customer;

//import com.example.bankingapi.account.AccountService;
import banking.fullstack.app.customer.Customer;
import banking.fullstack.app.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
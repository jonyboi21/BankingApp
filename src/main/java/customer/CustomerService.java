package customer;

//import com.example.bankingapi.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
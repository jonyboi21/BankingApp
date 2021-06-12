package banking.fullstack.app.customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer")
    public void createCustomer(@RequestBody Customer customer) {
    customerService.createCustomer(customer);
    }

    @GetMapping("/customer/")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/customer/{id}")
    public Customer getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }


    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }


    @PutMapping("/customer/{id}")
    public void updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        customerService.updateCustomer(customer,id);
    }


}


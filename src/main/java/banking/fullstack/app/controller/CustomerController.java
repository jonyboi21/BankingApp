package banking.fullstack.app.controller;
import banking.fullstack.app.exceptionhandling.CodeMessage;
import banking.fullstack.app.exceptionhandling.CodeMessageData;
import banking.fullstack.app.models.Customer;
import banking.fullstack.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping(path = "/api/v1")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            CodeMessageData response = new CodeMessageData(201, "Customer created", customerService.createCustomer(customer));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            CodeMessage error = new CodeMessage(404, "Error: could not create customer");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customer")
    public ResponseEntity<?> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        if(customers.isEmpty()){
            CodeMessage error = new CodeMessage(404, "Error: no customers found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CodeMessageData response = new CodeMessageData(200, "Success", customers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        Customer customer = customerService.getCustomerById(id).orElse(null);
        if(customer == null){
            CodeMessage error = new CodeMessage(404, "Error: customer with id " + id + " does not exist");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CodeMessageData response = new CodeMessageData(200, "Success", customer);
        return new ResponseEntity<> (response, HttpStatus.OK);
    }


    @DeleteMapping("/customer/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        if(!customerService.customerCheck(id)){
            CodeMessage exception = new CodeMessage(404,"Error: customer with id " + id + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
        customerService.deleteCustomer(id);
        CodeMessage response = new CodeMessage(200,"Customer deleted");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PutMapping("/customer/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        if(!customerService.customerCheck(id)){
            CodeMessage exception = new CodeMessage(404,"Error: customer with id " + id + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
        customerService.updateCustomer(customer, id);
        CodeMessage response = new CodeMessage(200, "Customer updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}


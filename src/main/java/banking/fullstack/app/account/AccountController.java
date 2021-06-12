package banking.fullstack.app.account;

import banking.fullstack.app.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//@RequestMapping(path = "/api/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/account")
    public void createAccount(@RequestBody Account account) {
        accountService.createAccount(account);
    }

    @GetMapping("/account/")
    public Iterable<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/account/{accountId}")
    public Optional<Account> getAccountByAccountId(@PathVariable Long accountId){
    return accountService.getAccountByAccountId(accountId);
    }

    @GetMapping("/customers/{customerId}/accounts")
    public Iterable<Account> getAllAccountsByCustomer(@PathVariable Long customerId){
    return accountService.getAllAccountsByCustomer(customerId);
    }

    @PutMapping("/account/{accountId}")
    public void updateAccount(@PathVariable Long accountId, @RequestBody Account account){
        accountService.updateAccount(account, accountId);
    }

    @DeleteMapping("/account/{accountId}")
    public void deleteAccount(@PathVariable Long accountId){
        accountService.deleteAccount(accountId);
    }
}
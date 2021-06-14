package banking.fullstack.app.services;

import banking.fullstack.app.models.Account;
import banking.fullstack.app.repositories.AccountRepository;
import banking.fullstack.app.models.Customer;
import banking.fullstack.app.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }

    public Iterable<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountByAccountId(Long accountId){
        return accountRepository.findById(accountId);
    }

    public Iterable<Account> getAllAccountsByCustomer(Long customerId){
        return accountRepository.findAllByCustomerId(customerId);
    }

    public boolean customerCheck(Long accountId){

        Customer customer = customerRepository.findById(accountId).orElse(null);
        return customer != null;
    }

    public boolean accountCheck(Long accountId){

        Account account = getAccountByAccountId(accountId).orElse(null);
        return account != null;
    }

    public void updateAccount(Account account, Long accountId){
        accountRepository.save(account);
    }

    public void deleteAccount(Long accountId){
        accountRepository.deleteById(accountId);
    }
}

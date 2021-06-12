package banking.fullstack.app.account;

import banking.fullstack.app.customer.Customer;
import banking.fullstack.app.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<Account> getAllAccounts(){
        List<Account> listOfAccounts = new ArrayList<>();
        accountRepository
                .findAll().forEach(listOfAccounts::add);
        return listOfAccounts;
    }

    public Account getAccountByAccountId(Long accountId){
        return accountRepository.findById(accountId).get();
    }

    public List<Account> getAllAccountsByCustomer(Long customerId){
        return accountRepository.findAllByCustomerId(customerId);
    }

    public boolean customerCheck(Long accountId){

        Customer customer = customerRepository.findById(accountId).orElse(null);
        return customer != null;
    }

    public boolean accountCheck(Long accountId){
        if (getAccountByAccountId(accountId) == null){
            return false;
        }
            return true;
    }

    public void updateAccount(Account account, Long accountId){
        account.setId(accountId);
        accountRepository.save(account);
    }

    public void deleteAccount(Long accountId){
        accountRepository.deleteById(accountId);
    }
}

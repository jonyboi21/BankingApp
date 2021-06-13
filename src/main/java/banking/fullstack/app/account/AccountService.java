package banking.fullstack.app.account;

import banking.fullstack.app.customer.Customer;
import banking.fullstack.app.customer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {

    Logger accountLog = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Iterable<Account> getAllAccounts() {

        accountLog.info("===== RETRIEVING ALL ACCOUNTS =====");
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountByAccountId(Long accountId) {

        accountLog.info("===== RETRIEVING ALL ACCOUNTS BY ACCOUNT ID =====");
        return accountRepository.findById(accountId);
    }

    public Iterable<Account> getAllAccountsByCustomer(Long customerId) {

        accountLog.info("===== RETRIEVING ALL ACCOUNTS BY CUSTOMER ID =====");
        return accountRepository.findAllByCustomerId(customerId);
    }

    public boolean customerCheck(Long accountId) {

        Customer customer = customerRepository.findById(accountId).orElse(null);
        return customer != null;
    }

    public boolean accountCheck(Long accountId) {

        Account account = getAccountByAccountId(accountId).orElse(null);
        return account != null;
    }

    public Account createAccount(Account account) {

        accountLog.info("===== CREATING ACCOUNT =====");
        return accountRepository.save(account);
    }

    public void updateAccount(Account account) {

        accountLog.info("===== UPDATING ACCOUNT =====");
        accountRepository.save(account);
    }

    public void deleteAccount(Long accountId) {

        accountLog.info("===== DELETING ACCOUNT =====");
        accountRepository.deleteById(accountId);
    }
}

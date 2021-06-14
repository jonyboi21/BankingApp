package banking.fullstack.app.services;

import banking.fullstack.app.controller.DepositController;
import banking.fullstack.app.models.Account;
import banking.fullstack.app.models.Deposit;
import banking.fullstack.app.repositories.AccountRepository;
import banking.fullstack.app.repositories.DepositRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepositService {

    Logger depositLog = LoggerFactory.getLogger(DepositController.class);

    @Autowired
    DepositRepository depositRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    public List<Deposit> getAllDeposits() {

        depositLog.info("===== RETRIEVING ALL DEPOSITS =====");
        List<Deposit> listOfDeposits = new ArrayList<>();
        depositRepository.findAll().forEach(listOfDeposits::add);
        return listOfDeposits;
    }

    public Iterable<Deposit> getAllDepositsByAccountId(Long accountId){

        depositLog.info("===== RETRIEVING ALL DEPOSITS BY ACCOUNT ID =====");
        return depositRepository.getDepositsByAccountId(accountId);
    }

    public Optional<Deposit> getDepositByAccountId(Long accountId){

        return depositRepository.getDepositByAccountId(accountId);
    }


    public Optional<Deposit> getDepositByDepositId(Long depositsId){

        depositLog.info("===== RETRIEVING DEPOSIT BY DEPOSIT ID =====");
        return depositRepository.findById(depositsId);
    }

    public Deposit createDeposit(Deposit deposit, Long accountId) {

        depositLog.info("===== CREATING DEPOSIT =====");
        Optional<Account> account = accountService.getAccountByAccountId(accountId);
        Double accountBalance = account.get().getBalance();
        Double depositAmount = deposit.getAmount();
        Double transaction = depositAmount + accountBalance;
        account.get().setBalance(transaction);
        return depositRepository.save(deposit);
    }

    public void updateDeposit(Deposit deposit, Long depositId){

        depositLog.info("===== UPDATING DEPOSIT =====");
        Account account = accountService.getAccountByAccountId(deposit.getPayeeId()).orElse(null);
        Double oldDepositAmount = depositRepository.findById(depositId).get().getAmount();
        Double accountBalance = account.getBalance();
        Double oldBalance = accountBalance - oldDepositAmount;
        account.setBalance(oldBalance);
        Double depositAmount = deposit.getAmount();
        Double transaction = oldBalance + depositAmount;
        account.setBalance(transaction);
        depositRepository.save(deposit);
    }

    public void deleteDeposit(Long depositsId) {

        depositLog.info("===== DELETING DEPOSIT =====");
        depositRepository.deleteById(depositsId);
    }

    public boolean depositCheck(Long accountId){

        Deposit deposit = depositRepository.findById(accountId).orElse(null);
        return deposit != null;
    }

    public boolean accountCheck(Long accountId){

        Account account = accountRepository.findById(accountId).orElse(null);
        return account != null;
    }
}
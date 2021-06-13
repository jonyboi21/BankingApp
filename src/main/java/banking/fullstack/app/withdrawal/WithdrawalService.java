package banking.fullstack.app.withdrawal;

import banking.fullstack.app.account.Account;
import banking.fullstack.app.account.AccountRepository;
import banking.fullstack.app.account.AccountService;
import banking.fullstack.app.deposit.Deposit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalService {

    Logger withdrawalLog = LoggerFactory.getLogger(WithdrawalController.class);

    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Autowired
    private AccountService accountService;

    public List<Withdrawal> getAllWithdrawals() {

        withdrawalLog.info("===== RETRIEVING ALL DEPOSITS =====");
        List<Withdrawal> listOfDeposits = new ArrayList<>();
        withdrawalRepository.findAll().forEach(listOfDeposits::add);
        return listOfDeposits;
    }

    public Iterable<Withdrawal> getAllWithdrawalsByAccountId(Long accountId) {

        return withdrawalRepository.getWithdrawalByAccountId(accountId);
    }

    public Withdrawal createWithdrawal(Withdrawal withdrawal, Long accountId) {

        withdrawalLog.info("===== CREATING WITHDRAWAL =====");
        Optional<Account> account = accountService.getAccountByAccountId(accountId);

        Double accountBalance = account.get().getBalance();
        Double withdrawalAmount = withdrawal.getAmount();

        Double transaction = accountBalance - withdrawalAmount;
        account.get().setBalance(transaction);

        return withdrawalRepository.save(withdrawal);
    }

    public Optional<Withdrawal> getWithdrawalByWithdrawalId(Long withdrawalId) {

        withdrawalLog.info("===== GETTING WITHDRAWAL BY WITHDRAWAL ID =====");
        return withdrawalRepository.findById(withdrawalId);
    }

    public void updateWithdrawal(Withdrawal withdrawal, Long withdrawalId) {

        withdrawalLog.info("===== UPDATING WITHDRAWAL =====");

        Account account = accountService.getAccountByAccountId(withdrawal.getPayerId()).orElse(null);

        Double oldWithdrawalAmount = withdrawalRepository.findById(withdrawalId).get().getAmount();

        Double accountBalance = account.getBalance();

        Double oldBalance = accountBalance + oldWithdrawalAmount;
        account.setBalance(oldBalance);

        Double depositAmount = withdrawal.getAmount();

        Double transaction = oldBalance - depositAmount;
        account.setBalance(transaction);

        withdrawalRepository.save(withdrawal);
    }

    public void deleteWithdrawal(Long withdrawalId) {

        withdrawalLog.info("===== DELETING WITHDRAWAL =====");
        withdrawalRepository.deleteById(withdrawalId);
    }
    public boolean withdrawalCheck(Long withdrawalId){

        Withdrawal withdrawal = withdrawalRepository.findById(withdrawalId).orElse(null);
        return withdrawal != null;
    }

    @Autowired
    AccountRepository accountRepository;
    public boolean accountCheck(Long accountId){

        Account account = accountRepository.findById(accountId).orElse(null);
        return account != null;
    }


    public boolean checkWithdrawPossible(Long accountId, Withdrawal withdrawal){
    if (accountService.getAccountByAccountId(accountId).get().getBalance() <= withdrawal.getAmount()){
        return false;
    }
        return true;
    }





}

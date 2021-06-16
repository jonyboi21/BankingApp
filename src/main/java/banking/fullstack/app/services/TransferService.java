package banking.fullstack.app.services;

import banking.fullstack.app.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransferService {

    @Autowired
    AccountService accountService;
    @Autowired
    WithdrawalService withdrawalService;

    @Autowired
    DepositService depositService;

    public void initiateTransfer(Long accountNumberRecipient, Double amount) {

        Optional<Account> recipientAccountOptinal = accountService.findByAccountNumber(accountNumber);
        recipientAccountOptinal.get().setBalance(recipientAccountOptinal.get().getBalance() + amount);
    }
}

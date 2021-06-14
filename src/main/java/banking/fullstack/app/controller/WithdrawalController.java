package banking.fullstack.app.controller;

import banking.fullstack.app.exceptionhandling.CodeData;
import banking.fullstack.app.exceptionhandling.CodeMessage;
import banking.fullstack.app.exceptionhandling.CodeMessageData;
import banking.fullstack.app.models.Withdrawal;
import banking.fullstack.app.repositories.AccountRepository;
import banking.fullstack.app.repositories.WithdrawalRepository;
import banking.fullstack.app.services.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1")
public class WithdrawalController {

    @Autowired
    WithdrawalService withdrawalService;

    @Autowired
    WithdrawalRepository withdrawalRepository;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/withdrawals")
    public ResponseEntity<?> getAllDeposits() {
        List<Withdrawal> withdrawals = withdrawalService.getAllWithdrawals();
        if(withdrawals.isEmpty()){
            CodeMessage error = new CodeMessage(404, "Error: no deposits found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        CodeMessageData response = new CodeMessageData(200, "Success", withdrawals);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/accounts/{accountId}/withdrawals")
    public ResponseEntity<?> getAllWithdrawalsByAccountId(@PathVariable Long accountId) {

       Iterable<Withdrawal> withdrawals =  withdrawalService.getAllWithdrawalsByAccountId(accountId);
        if(withdrawalRepository.getWithdrawalByAccountId(accountId).isEmpty()){
            CodeMessage exception = new CodeMessage(404,"Error: account with " + accountId + " does not exist");
        return new ResponseEntity<>(exception,HttpStatus.NOT_FOUND);
    }
        CodeData response = new CodeData(200, withdrawals);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/withdrawals/{withdrawalId}")
    public ResponseEntity<?> getWithdrawalById(@PathVariable Long withdrawalId){

        Optional<Withdrawal> withdrawal =  withdrawalService.getWithdrawalByWithdrawalId(withdrawalId);
        if(withdrawal.isEmpty()){
            CodeMessage exception = new CodeMessage(404,"Error: deposit with id " + withdrawalId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
        CodeData response = new CodeData(200, withdrawal);
        return new ResponseEntity<> (response, HttpStatus.OK);
    }

    @PostMapping("/accounts/{accountId}/withdrawals")
    public ResponseEntity<?> createWithdrawal(@PathVariable Long accountId, @RequestBody Withdrawal withdrawal) {

        try {
            if (!withdrawalService.accountCheck(accountId)) {
                CodeMessage exception = new CodeMessage(404, "Error: account with id " + accountId + " does not exist");
                return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
            } else if (withdrawal.getAmount() >= accountRepository.findById(accountId).get().getBalance()) {
                CodeMessage exception = new CodeMessage(400, "Error: over withdrawal");
                return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
            } else if (withdrawal.getAmount() <= 0) {
                CodeMessage exception = new CodeMessage(400, "Error: withdrawal amount must be greater than zero");
                return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
            } else {
                Withdrawal w1 = withdrawalService.createWithdrawal(withdrawal, accountId);
                CodeMessageData response = new CodeMessageData(201, "Withdrawal Created", w1);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } } catch (Exception e){
            CodeMessage error = new CodeMessage(404, "Error: could not create withdrawal");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/withdrawals/{withdrawalId}")
    public ResponseEntity<?> updateWithdrawal(@PathVariable Long withdrawalId, @RequestBody Withdrawal withdrawal) {
        if (!withdrawalService.withdrawalCheck(withdrawalId)) {
            CodeMessage exception = new CodeMessage(404,"Error: withdrawal with id " + withdrawalId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }

        withdrawalService.updateWithdrawal(withdrawal, withdrawalId);
        CodeMessage response = new CodeMessage(200, "Withdrawal updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/withdrawals/{withdrawalId}")
    public ResponseEntity<?> deleteWithdrawal(@PathVariable Long withdrawalId){

    if(!withdrawalService.withdrawalCheck(withdrawalId)){
        CodeMessage exception = new CodeMessage(404,"Error: withdrawal with id " + withdrawalId + " does not exist");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

        withdrawalService.deleteWithdrawal(withdrawalId);
        CodeMessage response = new CodeMessage(200, "Withdrawal deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

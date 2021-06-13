package banking.fullstack.app.deposit;

import banking.fullstack.app.account.AccountService;
import banking.fullstack.app.customer.Customer;
import banking.fullstack.app.exceptionhandling.CodeData;
import banking.fullstack.app.exceptionhandling.CodeMessage;
import banking.fullstack.app.exceptionhandling.CodeMessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/hanover/api/v2")
public class DepositController {

    @Autowired
    private DepositService depositService;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    AccountService accountService;

    @GetMapping("/deposits")
    public ResponseEntity<?> getAllDeposits() {
        List<Deposit> deposits = depositService.getAllDeposits();
        if(deposits.isEmpty()){
            CodeMessage error = new CodeMessage(404, "Error: no deposits found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        CodeMessageData response = new CodeMessageData(200, "Success", deposits);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/accounts/{accountId}/deposits")
    public ResponseEntity<?> getAllDepositsByAccountId(@PathVariable Long accountId) {

        Iterable<Deposit> deposits = depositService.getAllDepositsByAccountId(accountId);
        if (deposits.iterator().hasNext()) {
            CodeData response = new CodeData(200, deposits);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        if (!accountService.accountCheck(accountId)) {
            CodeMessage exception1 = new CodeMessage(404, "Error: account with " + accountId + " does not exist");
            return new ResponseEntity<>(exception1, HttpStatus.NOT_FOUND);
        }
        CodeMessage exception = new CodeMessage(404, "Error: no deposits found");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/deposits/{depositsId}")
    public ResponseEntity<?> getDepositById(@PathVariable Long depositsId) {
        Optional<Deposit> deposit = depositService.getDepositByDepositId(depositsId);
        if (deposit.isEmpty()) {
            CodeMessage exception = new CodeMessage(404, "Error: deposit with id " + depositsId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
        CodeData response = new CodeData(200, deposit);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/accounts/{accountId}/deposits")
    public ResponseEntity<?> createDeposit(@PathVariable Long accountId, @RequestBody Deposit deposit) {
        try {
            if (!depositService.accountCheck(accountId)) {
                CodeMessage exception = new CodeMessage(404,"Error: account with id " + accountId + " does not exist");
                return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
            } else if (deposit.getAmount() <= 0) {
                CodeMessage exception = new CodeMessage(400,"Error: deposit amount must be greater than zero");
                return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
            } else {
                Deposit d1 = depositService.createDeposit(deposit, accountId);
                CodeMessageData response = new CodeMessageData(201, "Deposit created", d1);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        } catch(Exception e) {
            CodeMessage error = new CodeMessage(404, "Error: could not create deposit");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

    }


    @PutMapping("/deposits/{depositsId}")
    public ResponseEntity<?> updateDeposit(@PathVariable Long depositsId, @RequestBody Deposit deposit){

        if(!depositService.depositCheck(depositsId)){
            CodeMessage exception = new CodeMessage(404, "Error: deposit with id " + depositsId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
        depositService.updateDeposit(deposit, depositsId);
        CodeMessage response = new CodeMessage(200, "Deposit updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deposits/{depositsId}")
    public ResponseEntity<?> deleteDeposit(@PathVariable Long depositsId) {

        if (!depositService.depositCheck(depositsId)){
            CodeMessage exception = new CodeMessage(404, "Error: deposit with id " + depositsId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }

        depositService.deleteDeposit(depositsId);
        CodeMessage response = new CodeMessage(200, "Deposit deleted");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
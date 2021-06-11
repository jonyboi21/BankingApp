package banking.fullstack.app.deposit;

import banking.fullstack.app.exceptionhandling.CodeData;
import banking.fullstack.app.exceptionhandling.CodeMessage;
import banking.fullstack.app.exceptionhandling.CodeMessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class DepositController {

    @Autowired
    private DepositService depositService;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/accounts/{accountId}/deposits", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDepositsByAccountId(@PathVariable Long accountId) {

        Iterable<Deposit> deposits = depositService.getAllDepositsByAccountId(accountId);
        if (deposits.iterator().hasNext()) {
            CodeData response = new CodeData(200, deposits);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        if (!accountService.accountCheck(accountId)) {
            CodeMessage exception1 = new CodeMessage(404, "Account not found");
            return new ResponseEntity<>(exception1, HttpStatus.NOT_FOUND);
        }
        CodeMessage exception = new CodeMessage(404, "There are no deposits");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/deposits/{depositsId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDepositById(@PathVariable Long depositsId) {
        Optional<Deposit> deposit = depositService.getDepositByDepositId(depositsId);
        if (deposit.isEmpty()) {
            CodeMessage exception = new CodeMessage(404, "error fetching deposit with id " + depositsId);
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
        CodeData response = new CodeData(200, deposit);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/{accountId}/deposits", method = RequestMethod.POST)
    public ResponseEntity<?> createDeposit(@PathVariable Long accountId, @RequestBody Deposit deposit) {
        try {
            if (!depositService.accountCheck(accountId)) {
                CodeMessage exception = new CodeMessage("Error creating deposit: Account not found");
                return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
            } else if (deposit.getAmount() <= 0) {
                CodeMessage exception = new CodeMessage("Error creating deposit: Deposit amount must be greater than zero");
                return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
            } else {
                Deposit d1 = depositService.createDeposit(deposit, accountId);
                CodeMessageData response = new CodeMessageData(201, "Created deposit and added it to the account", d1);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        } catch(Exception e) {
            CodeMessage error = new CodeMessage(404, "Error creating deposit");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

    }


    @RequestMapping(value = "/deposits/{depositsId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDeposit(@PathVariable Long depositsId, @RequestBody Deposit deposit){

        if(!depositService.depositCheck(depositsId)){
            CodeMessage exception = new CodeMessage(404, "Deposit ID does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
        depositService.updateDeposit(deposit, depositsId);
        return new ResponseEntity<>("Accepted deposit modification", HttpStatus.OK);
    }

    @RequestMapping(value = "/deposits/{depositsId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDeposit(@PathVariable Long depositsId) {

        if (!depositService.depositCheck(depositsId)){
            CodeMessage exception = new CodeMessage(404, "This id does not exist in deposits");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }

        depositService.deleteDeposit(depositsId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
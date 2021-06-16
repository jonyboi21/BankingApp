package banking.fullstack.app.controller;

import banking.fullstack.app.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/api/v1")
public class TransferController {

    @Autowired
    TransferService transferService;

    public void P2PTransfer(Long accountNumber, Double amount){

        transferService.initiateTransfer(accountNumber,amount);

    }


}

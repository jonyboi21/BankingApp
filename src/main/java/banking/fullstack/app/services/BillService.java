package banking.fullstack.app.services;

import banking.fullstack.app.controller.BillController;
import banking.fullstack.app.models.Account;
import banking.fullstack.app.models.Bill;
import banking.fullstack.app.repositories.AccountRepository;
import banking.fullstack.app.repositories.BillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Iterable<Bill> getAllBills(){
        return billRepository.findAll();
    }

    public List<Bill> getBillsByAccountId(Long id) {
        return billRepository.getBillByAccountId(id);
    }

    public Bill getBillByBillId(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    public List<Bill> getAllBillsByCustomerId(Long customer_id) {
        List<Long> accountId = billRepository.getAccountIdThatMatchesCustomerId(customer_id);
        return billRepository.getBillsByCustomerId(accountId);
    }

    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    public void updateBill(Bill bill){
        billRepository.save(bill);
    }

    public void deleteBill(Long id){
        billRepository.deleteById(id);
    }

    public boolean billCheck(Long billId){

        Bill bill = billRepository.findById(billId).orElse(null);
        return bill != null;
    }
}


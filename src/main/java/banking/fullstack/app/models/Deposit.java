package banking.fullstack.app.models;

import javax.persistence.*;

@Entity
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String transactionDate;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private Long payeeId;
    @Column(nullable = false)
    private String medium;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Long payeeId) {
        this.payeeId = payeeId;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

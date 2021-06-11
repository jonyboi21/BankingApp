package banking.fullstack.app.deposit;

import javax.persistence.*;

    @Entity
    public class Deposit {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @Column(nullable = false)
        private String type;
        @Column(nullable = false)
        private String transaction_date;
        @Column(nullable = false)
        private String status;
        @Column(nullable = false)
        private Long payee_id;
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

        public String getTransaction_date() {
            return transaction_date;
        }

        public void setTransaction_date(String transaction_date) {
            this.transaction_date = transaction_date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Long getPayee_id() {
            return payee_id;
        }

        public void setPayee_id(Long payee_id) {
            this.payee_id = payee_id;
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

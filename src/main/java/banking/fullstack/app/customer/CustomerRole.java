package banking.fullstack.app.customer;

import javax.validation.constraints.NotNull;

public enum CustomerRole {
    USER("USER"),
    ADMIN("ADMIN");

    @NotNull
    private final String role;

    CustomerRole(String role){
        this.role =role;
    }
    public String getRole() {
        return role;
    }
}

package banking.fullstack.app.customer;


import org.apache.tomcat.jni.Address;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Customer implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private CustomerRole customerRole;
    private Set<Address> addressSet;
    private Boolean locked = false;
    private Boolean enabled = false;


    public Customer() {
    }


    public Long getCustomerId() {
        return id;
    }

    public void setCustomerId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return name;
    }

    public void setCustomerName(String name) {
        this.name = name;
    }

    public String getCustomerEmail() {
        return email;
    }

    public void setCustomerEmail(String email) {
        this.email = email;
    }

    public void setCustomerPassword(String password) {
        this.password = password;
    }

    public Boolean getCustomerLocked() {
        return locked;
    }

    public void setCustomerLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getCustomerEnabled() {
        return enabled;
    }

    public void setCustomerEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(customerRole.name());
        return Collections.singletonList(authority);
    };


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", locked=" + locked +
                ", enabled=" + enabled +
                '}';
    }
}

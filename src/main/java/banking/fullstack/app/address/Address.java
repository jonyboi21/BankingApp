package banking.fullstack.app.address;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(nullable = false)
    public String streetNumber;
    @Column(nullable = false)
    public String streetName;
    @Column(nullable = false)
    public String city;
    @Column(nullable = false)
    public String state;
    @Column(nullable = false)
    public String zip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String street_number) {
        this.streetNumber = street_number;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String street_name) {
        this.streetName = street_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
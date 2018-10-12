package com.teamelixir.usermicro.app.customer;
import com.teamelixir.usermicro.app.user.User;
import javax.persistence.*;

@Entity
@Table(name = "user_customer")
public class CustomerModel extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="contactNum")
    private String contactNum;

    @Column(name="address")
    private String address;

    @Column(name="role")
    private String role;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getContactNum() {
        return contactNum;
    }

    @Override
    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRole() {
        this.role = "USER";
    }

    @Override
    public String getRole() {
        return "USER";
    }

}

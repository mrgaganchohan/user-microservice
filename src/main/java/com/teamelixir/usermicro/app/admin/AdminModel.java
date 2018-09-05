package com.teamelixir.usermicro.app.admin;

import com.teamelixir.usermicro.app.user.User;

import javax.persistence.*;

@Entity
@Table(name = "user_admin")
public class AdminModel extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="contactNum")
    private String contactNum;

    @Column(name="role")
    private String role;

    @Column(name="designation")
    private String designation;

    @Column(name="office")
    private String office;

    @Column(name="username")
    private String username;

    @Override
    public long getId() {
        return id;
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

    public void setRole() {
        this.role = "ADMIN";
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername() {
        this.username = "COG_ADMIN_" + this.getId();
    }
}

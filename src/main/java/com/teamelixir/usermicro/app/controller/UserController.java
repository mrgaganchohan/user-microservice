package com.teamelixir.usermicro.app.controller;

import com.teamelixir.usermicro.app.admin.AdminModel;
import com.teamelixir.usermicro.app.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private AdminRepository adminRepository;

    //Add a user to the db - username (PK) cannot be null.
    @RequestMapping(value="/admin/add", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity addAdmin(@RequestBody AdminModel admin) {
        AdminModel adminUser = new AdminModel();

        adminUser.setName(admin.getName());
        adminUser.setEmail(admin.getEmail());
        adminUser.setRole();
        adminUser.setContactNum(admin.getContactNum());
        adminUser.setDesignation(admin.getDesignation());
        adminUser.setOffice(admin.getOffice());
        adminUser.setUsername(admin.getUsername().toLowerCase());

        boolean exists = adminRepository.existsById(admin.getUsername());

        if(exists) {
            return new ResponseEntity("Username already exists.", HttpStatus.CONFLICT);
        }

        adminRepository.save(adminUser);

        return new ResponseEntity(adminUser, HttpStatus.CREATED);
    }

    //Check if user exists in the db, return that user - otherwise return error message.
    @RequestMapping(value = "/admin/{username}")
    public ResponseEntity findByUsername(@PathVariable String username) {
        AdminModel adminUser = adminRepository.findAdminModelByUsername(username);

        if(adminUser == null){
            return new ResponseEntity("Cannot find user with username '" + username + "'", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(adminUser, HttpStatus.FOUND);
    }

    //Delete a user based on their unique username.
    @RequestMapping(value = "/admin/delete/{username}")
    @Transactional
    public ResponseEntity deleteAdminUser(@PathVariable String username) {
        String adminUsername = username.toLowerCase();
        //Check to see if user exists first
        boolean exists = adminRepository.existsById(adminUsername);

        if(!exists) {
            return new ResponseEntity("'" + adminUsername + "' does not exist. Cannot delete user.", HttpStatus.NOT_FOUND);
        }

        adminRepository.deleteAdminModelByUsername(adminUsername);

        return new ResponseEntity("Deleted '" + adminUsername + "' successfully.", HttpStatus.OK);
    }



}

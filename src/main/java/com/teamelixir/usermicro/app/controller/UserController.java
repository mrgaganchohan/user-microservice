package com.teamelixir.usermicro.app.controller;

import com.teamelixir.usermicro.app.admin.AdminModel;
import com.teamelixir.usermicro.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import javax.persistence.RollbackException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(value="/admin/add", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity addAdmin(@RequestBody AdminModel admin) {
        AdminModel adminUser = new AdminModel();

        adminUser.setName(admin.getName());
        adminUser.setEmail(admin.getEmail());
        adminUser.setRole();
        adminUser.setContactNum(admin.getContactNum());
        adminUser.setDesignation(admin.getDesignation());
        adminUser.setOffice(admin.getOffice());
        adminUser.setUsername(admin.getUsername());

         boolean added = service.addAdmin(adminUser);

         if(!added) {
             return ResponseEntity.status(HttpStatus.CONFLICT)
                     .contentType(MediaType.TEXT_PLAIN)
                     .body("Cannot add username '" + admin.getUsername()+ "'");
         }

        return new ResponseEntity(adminUser, HttpStatus.OK);

    }

    @RequestMapping("/test")
    public String test() {
        return "This works";
    }


}

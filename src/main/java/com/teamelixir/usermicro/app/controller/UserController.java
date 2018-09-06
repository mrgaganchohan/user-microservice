package com.teamelixir.usermicro.app.controller;

import com.teamelixir.usermicro.app.admin.AdminModel;
import com.teamelixir.usermicro.app.customer.CustomerModel;
import com.teamelixir.usermicro.app.repository.AdminRepository;
import com.teamelixir.usermicro.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.boot.configurationprocessor.json.JSONObject;

@RestController
@RequestMapping(value = "/users")
@SuppressWarnings("unchecked")
public class UserController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;


    //Add an ADMIN to the db - username (PK) cannot be null.
    @RequestMapping(value="/{userType}/add", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity addAdmin(@PathVariable String userType, @RequestBody String data) {
        JSONObject jsonObject = null;

        if(userType.equalsIgnoreCase("admin")) {
            try {
                jsonObject = new JSONObject(data);
                AdminModel adminUser= new AdminModel();
                adminUser.setName(jsonObject.getString("name"));
                adminUser.setEmail(jsonObject.getString("email"));
                adminUser.setRole();
                adminUser.setContactNum(jsonObject.getString("contactNum"));
                adminUser.setDesignation(jsonObject.getString("designation"));
                adminUser.setOffice(jsonObject.getString("office"));
                adminUser.setUsername(jsonObject.getString("username").toLowerCase());

                boolean exists = adminRepository.existsById(adminUser.getUsername());

                if(exists) {
                    String username = jsonObject.getString("username");
                    return new ResponseEntity("Admin with username="+username +" already exists.", HttpStatus.CONFLICT);
                }

                adminRepository.save(adminUser);
                return new ResponseEntity(adminUser, HttpStatus.CREATED);
            }
            catch(Exception e) {
               e.printStackTrace();
            }
        }
        if(userType.equalsIgnoreCase("customer")) {
            try {
                jsonObject = new JSONObject(data);
                CustomerModel customerUser = new CustomerModel();
                customerUser.setName(jsonObject.getString("name"));
                customerUser.setEmail(jsonObject.getString("email"));
                customerUser.setRole();
                customerUser.setContactNum(jsonObject.getString("contactNum"));
                customerUser.setUsername(jsonObject.getString("username").toLowerCase());

                boolean exists = adminRepository.existsById(customerUser.getUsername());

                if(exists) {
                    String username = jsonObject.getString("username");
                    return new ResponseEntity("Customer with username="+username +" already exists.", HttpStatus.CONFLICT);
                }

                customerRepository.save(customerUser);

                return new ResponseEntity(customerUser, HttpStatus.CREATED);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity("The path you requested does not exist.", HttpStatus.BAD_REQUEST);
    }

    //Check if user exists in the db, return that user - otherwise return error message.
    @RequestMapping(value = "/{userType}/{username}")
    public ResponseEntity findByUsername(@PathVariable String userType, @PathVariable String username) {
        if(userType.equalsIgnoreCase("admin")){
            AdminModel adminUser = adminRepository.findAdminModelByUsername(username);

            if(adminUser == null){
                return new ResponseEntity("Cannot find admin user with username '" + username + "'", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity(adminUser, HttpStatus.FOUND);
        }
        if(userType.equalsIgnoreCase("customer")){
            CustomerModel customerUser = customerRepository.findCustomerModelByUsername(username);

            if(customerUser == null){
                return new ResponseEntity("Cannot find customer user with username '" + username + "'", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity(customerUser, HttpStatus.FOUND);
        }
        return new ResponseEntity("The path requested does not exist.", HttpStatus.NOT_FOUND);
    }

    //Delete a user based on their unique username.
    @RequestMapping(value = "/{userType}/delete/{username}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable String userType, @PathVariable String username) {

        if(userType.equalsIgnoreCase("admin")) {
            String adminUsername = username.toLowerCase();
            //Check to see if user exists first
            boolean exists = adminRepository.existsById(adminUsername);

            if(!exists) {
                return new ResponseEntity("'" + adminUsername + "' does not exist. Cannot delete user.", HttpStatus.NOT_FOUND);
            }

            adminRepository.deleteAdminModelByUsername(adminUsername);
            return new ResponseEntity("Deleted '" + adminUsername + "' successfully.", HttpStatus.OK);
        }
        if(userType.equalsIgnoreCase("customer")) {
            String customerUsername = username.toLowerCase();
            //Check to see if user exists first
            boolean exists = customerRepository.existsById(customerUsername);

            if(!exists) {
                return new ResponseEntity("'" + customerUsername + "' does not exist. Cannot delete user.", HttpStatus.NOT_FOUND);
            }

            customerRepository.deleteCustomerModelByUsername(customerUsername);
            return new ResponseEntity("Deleted '" + customerUsername + "' successfully.", HttpStatus.OK);
        }
        return new ResponseEntity("The path requested does not exist.", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{userType}/getAll")
    public ResponseEntity getAll(@PathVariable String userType) {

        if(userType.equalsIgnoreCase("admin")) {
            Iterable<AdminModel> allAdmins = adminRepository.findAll();

            //May not add this in as empty array may be handled on frontend.
//        if(!allAdmins.iterator().hasNext()){
//            return new ResponseEntity("There are no entries in db.", HttpStatus.CONFLICT);
//        }
            return new ResponseEntity(allAdmins, HttpStatus.OK);
        }
        if(userType.equalsIgnoreCase("customer")) {
            Iterable<CustomerModel> allCustomers = customerRepository.findAll();

            //May not add this in as empty array may be handled on frontend.
//        if(!allAdmins.iterator().hasNext()){
//            return new ResponseEntity("There are no entries in db.", HttpStatus.CONFLICT);
//        }
            return new ResponseEntity(allCustomers, HttpStatus.OK);
        }
        return new ResponseEntity("The path requested does not exist.", HttpStatus.NOT_FOUND);
    }
}

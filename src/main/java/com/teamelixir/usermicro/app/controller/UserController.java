package com.teamelixir.usermicro.app.controller;

import com.teamelixir.usermicro.app.admin.AdminModel;
import com.teamelixir.usermicro.app.customer.CustomerModel;
import com.teamelixir.usermicro.app.repository.AdminRepository;
import com.teamelixir.usermicro.app.repository.CustomerRepository;
import org.apache.commons.lang.WordUtils;
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
    @PostMapping(value="/{userType}/add", produces = {"application/json"})
    public ResponseEntity addUser(@PathVariable String userType, @RequestBody String data) {
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

                AdminModel exists = adminRepository.findAdminModelByEmail(adminUser.getEmail());

                if(exists != null) {
                    String email = jsonObject.getString("email");
                    return new ResponseEntity("Admin with email="+email +" already exists.", HttpStatus.CONFLICT);
                }

                adminRepository.save(adminUser);
                return new ResponseEntity(adminUser, HttpStatus.CREATED);
            }
            catch(Exception e) {
               e.printStackTrace();
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

                CustomerModel exists = customerRepository.findCustomerModelByEmail(customerUser.getEmail());

                if(exists != null) {
                    String email = jsonObject.getString("email");
                    return new ResponseEntity("Customer with username="+email +" already exists.", HttpStatus.CONFLICT);
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
    @GetMapping(value = "/{userType}/{email}")
    public ResponseEntity findByEmail(@PathVariable String userType, @PathVariable String email) {
        if(userType.equalsIgnoreCase("admin")){
            AdminModel adminUser = adminRepository.findAdminModelByEmail(email);

            if(adminUser == null){
                return new ResponseEntity("Cannot find admin user with email '" + email + "'", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity(adminUser, HttpStatus.OK);
        }
        if(userType.equalsIgnoreCase("customer")){
            CustomerModel customerUser = customerRepository.findCustomerModelByEmail(email);

            if(customerUser == null){
                return new ResponseEntity("Cannot find customer user with email '" + email + "'", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity(customerUser, HttpStatus.OK);
        }
        return new ResponseEntity("The path requested does not exist.", HttpStatus.NOT_FOUND);
    }

    //Delete a user based on their unique username.
    @DeleteMapping(value = "/{userType}/delete/{email}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable String userType, @PathVariable String email) {

        if(userType.equalsIgnoreCase("admin")) {
            //Check to see if user exists first
            AdminModel adminUser = adminRepository.findAdminModelByEmail(email);

            if(adminUser == null) {
                return new ResponseEntity("'" + email + "' does not exist. Cannot delete user.", HttpStatus.NOT_FOUND);
            }

            adminRepository.deleteAdminModelByEmail(email);
            return new ResponseEntity("Deleted '" + email + "' successfully.", HttpStatus.OK);
        }
        if(userType.equalsIgnoreCase("customer")) {
            //Check to see if user exists first
            CustomerModel customerUser = customerRepository.findCustomerModelByEmail(email);

            if(customerUser ==  null) {
                return new ResponseEntity("'" + email + "' does not exist. Cannot delete user.", HttpStatus.NOT_FOUND);
            }

            customerRepository.deleteCustomerModelByEmail(email);
            return new ResponseEntity("Deleted '" + email + "' successfully.", HttpStatus.OK);
        }
        return new ResponseEntity("The path requested does not exist.", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{userType}/getAll")
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

    @PutMapping(value="/{userType}/update/{email}")
    public ResponseEntity editUserDetails(@PathVariable String userType, @PathVariable String email, @RequestBody String data) {
        JSONObject jsonObject = null;

        if(userType.equalsIgnoreCase("admin")) {
          try {
              jsonObject = new JSONObject(data);

              AdminModel adminUser = adminRepository.findAdminModelByEmail(email);

              if(adminUser != null) {
                  adminUser.setName(WordUtils.capitalizeFully(jsonObject.getString("name")));
                  adminUser.setRole();
                  adminUser.setContactNum(jsonObject.getString("contactNum"));
                  adminUser.setDesignation(jsonObject.getString("designation"));
                  adminUser.setOffice(jsonObject.getString("office"));

                  adminRepository.save(adminUser);
                  return new ResponseEntity(adminUser, HttpStatus.OK);
              }

              return new ResponseEntity("Cannot find user in db, so cannot update non-existent user.", HttpStatus.NOT_FOUND);

          }
          catch(Exception e) {
              e.printStackTrace();
          }
        }

        if(userType.equalsIgnoreCase("customer")) {
            try {
                jsonObject = new JSONObject(data);

                CustomerModel customerUser = customerRepository.findCustomerModelByEmail(email);

                if(customerUser != null) {
                    customerUser.setName(jsonObject.getString("name"));
                    customerUser.setRole();
                    customerUser.setContactNum(jsonObject.getString("contactNum"));

                    customerRepository.save(customerUser);
                    return new ResponseEntity(customerUser, HttpStatus.OK);
                }

                return new ResponseEntity("Cannot find user in db, so cannot update non-existent user.", HttpStatus.NOT_FOUND);
            }
          catch(Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity("The path requested does not exist.", HttpStatus.NOT_FOUND);
    }
}

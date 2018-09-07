package com.teamelixir.usermicro.app.repository;

import com.teamelixir.usermicro.app.customer.CustomerModel;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerModel, String> {

    CustomerModel findCustomerModelByEmail(String email);
    void deleteCustomerModelByEmail(String email);
}

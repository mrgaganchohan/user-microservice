package com.teamelixir.usermicro.app.repository;

import com.teamelixir.usermicro.app.customer.CustomerModel;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerModel, String> {

    CustomerModel findCustomerModelByUsername(String username);
    void deleteCustomerModelByUsername(String username);

}

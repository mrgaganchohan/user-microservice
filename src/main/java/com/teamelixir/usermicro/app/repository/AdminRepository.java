package com.teamelixir.usermicro.app.repository;

import com.teamelixir.usermicro.app.admin.AdminModel;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<AdminModel, String> {

    AdminModel findAdminModelByEmail(String email);
    void deleteAdminModelByEmail(String email);

}

package com.teamelixir.usermicro.app.admin;


import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface AdminDAO {

    //add user
    boolean addAdmin(AdminModel admin);

    //get user
    AdminModel findAdminById(long id);

    //update user
    void updateAdminDetails(AdminModel admin);

    //delete user
    void deleteAdminById(long id);

    //get all admins
    List getAllAdmins();

}

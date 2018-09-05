package com.teamelixir.usermicro.app.service;

import com.teamelixir.usermicro.app.admin.AdminDAO;
import com.teamelixir.usermicro.app.admin.AdminModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.RollbackException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service("userService")
public class UserService {

    @Autowired
    private AdminDAO adminDao;

    //ADMIN OPERATIONS

    public boolean addAdmin(AdminModel admin) {
        return adminDao.addAdmin(admin);
    }

    AdminModel findAdminById(long id){
       return adminDao.findAdminById(id);
    }

    public void updateAdminDetails(AdminModel admin){
        adminDao.updateAdminDetails(admin);
    }

    public void deleteAdminById(long id){
        adminDao.deleteAdminById(id);
    }

    public List getAllAdmins(){
        return adminDao.getAllAdmins();
    }
}

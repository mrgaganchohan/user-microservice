package com.teamelixir.usermicro.app.admin;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.sql.SQLException;

import java.util.List;
import java.util.logging.Logger;

@Repository("adminDAO")
@EnableJpaRepositories
public class AdminDaoImpl implements AdminDAO {

    Logger logger = Logger.getLogger("ADMIN DAO");

    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public boolean addAdmin(AdminModel admin) {
        logger.info("Adding a new admin user!");
        Session session = getSession();
        Transaction transaction = getSession().beginTransaction();

        try {
           session.save(admin);
           transaction.commit();
           session.close();
           return true;
       }
       catch(RollbackException e) {

           System.out.println("ROLLBACK EXCEPTION!!!!!!!");

           if(transaction != null) {
               transaction.rollback();
           }
           return false;
       }
    }

    @Override
    public AdminModel findAdminById(long id) {
        Session session = getSession();
        Transaction transaction = getSession().beginTransaction();

        AdminModel admin = session.load(AdminModel.class, id);

        return admin;
    }

    @Override
    public void updateAdminDetails(AdminModel admin) {
        Session session = getSession();
        Transaction transaction = getSession().beginTransaction();

        session.update(admin);
        transaction.commit();

        session.close();
    }

    @Override
    public void deleteAdminById(long id) {
        Session session = getSession();
        Transaction transaction = getSession().beginTransaction();

        AdminModel user = session.load(AdminModel.class, id);
        session.delete(user);

        transaction.commit();
        session.close();
    }

    @Override
    public List getAllAdmins() {
        List<AdminModel> userList;
        Session session = getSession();

        userList = session.createCriteria(AdminModel.class).list();

        return userList;
    }
}

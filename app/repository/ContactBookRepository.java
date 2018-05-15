package repository;

import models.UserContact;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

//import javax.persistence.EntityManager;

//import javax.persistence.EntityManager;

/**
 * Created by tripti on 01/04/18.
 */

public class ContactBookRepository implements IContactBookRepository {

    @Override
    public UserContact createUser(UserContact userContact) {
        try {
            return JPA.withTransaction("default",true, () -> {
                if (UserContact.findUserWithEmailId(userContact.getEmail()) == null) {
                    JPA.em().getTransaction().begin();
                    JPA.em().persist(userContact);
                    JPA.em().getTransaction().commit();
                    System.out.printf("Generated Contact ID = " + userContact.getUserId());
                    return userContact;
                }
                else {
                    System.out.printf("Already Persist with Contact ID = " + UserContact.findUserWithEmailId(userContact.getEmail()).getUserId());
                    return null;
                }
            });
        }
        catch (Throwable throwable ) {
            Logger.error("{} {} \n{}", "error while creating user contact with email => ", userContact.getEmail(), throwable.getCause());
        }
        return null;
    }

    @Override
    public void deleteUser(String emailId) {
        try {
            if (UserContact.findUserWithEmailId(emailId) == null) {
                return;
            }
            else {
                Long userId = UserContact.findUserWithEmailId(emailId).getUserId();
                JPA.em().getTransaction().begin();
                JPA.em().remove(UserContact.findUserWithEmailId(emailId));
                JPA.em().getTransaction().commit();
                System.out.printf("Removed Contact ID = " + userId);
            }
        }
        catch (Throwable throwable) {
            Logger.error("{} {} \n{}", "error while deketing user contact with email => ", emailId, throwable.getCause());
        }
        return;
    }

    @Override
    public UserContact editUserDetails(UserContact userContact) {
        try {
            return JPA.withTransaction("default",true, () -> {
                if (UserContact.findUserWithEmailId(userContact.getEmail()) != null) {
                    JPA.em().getTransaction().begin();
                    JPA.em().merge(userContact);
                    JPA.em().getTransaction().commit();
                    System.out.printf("Updated Contact ID = " + userContact.getUserId());
                    return userContact;
                }
                else {
                    JPA.em().getTransaction().begin();
                    JPA.em().persist(userContact);
                    JPA.em().getTransaction().commit();
                    System.out.printf("Generated Contact ID = " + userContact.getUserId());
                    return userContact;
                }
            });
        }
        catch (Throwable throwable ) {
            Logger.error("{} {} \n{}", "error while updating user contact with email => ", userContact.getEmail(), throwable.getCause());
        }
        return null;
    }

    @Override
    public List<UserContact> getAllUsers() {
        return UserContact.getAllUsers();
    }
}

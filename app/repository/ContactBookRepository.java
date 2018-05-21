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
//                https://stackoverflow.com/questions/17027398/java-lang-illegalargumentexception-removing-a-detached-instance-com-test-user5
                JPA.withTransaction( () -> {
                    UserContact contact = UserContact.findUserWithEmailId(emailId);
                    //transaction is already active. No need to open a new transaction again
                    if (JPA.em().isOpen()) {
                        //remove works only on entities which are managed in the current transaction/context
                        JPA.em().remove(JPA.em().contains(contact) ? contact : JPA.em().merge(contact));
                    }
                    else {
                        JPA.em().getTransaction().begin();
                        //remove works only on entities which are managed in the current transaction/context
                        JPA.em().remove(JPA.em().contains(contact) ? contact : JPA.em().merge(contact));
                        JPA.em().getTransaction().commit();
                    }
                    System.out.printf("Removed Contact ID = " + contact.getUserId());
                });
            }
        }
        catch (Throwable throwable) {
            Logger.error("{} {} \n{}", "error while deleting user contact with email => ", emailId, throwable.getCause());
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

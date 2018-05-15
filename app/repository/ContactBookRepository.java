package repository;

import models.UserContact;
import play.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by tripti on 01/04/18.
 */

public class ContactBookRepository implements IContactBookRepository {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    ContactBookRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("defaultPersistenceUnit");
    }

    @Override
    public UserContact createUser(UserContact userContact) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            Query query = entityManager.createQuery("select u from UserContact u where u.email = :email");
            query.setParameter("email",userContact.getEmail() );
            UserContact userContactInDb = (UserContact)query.getSingleResult();
            if (userContactInDb == null) {
                entityManager.getTransaction().begin();
                entityManager.persist(userContact);
                entityManager.getTransaction().commit();
                System.out.printf("Generated Contact ID = " + userContact.getUserId());
                return userContact;
            }
            else {
                System.out.printf("Already Persist with Contact ID = " + userContactInDb.getUserId());
            }
        }
        catch (Throwable throwable ) {
            Logger.error("{} {} \n{}", "error while finding user onject for", userContact.getEmail(), throwable.getCause());
        }
        return null;
    }

    @Override
    public void deleteUser(String emailId) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        UserContact userContact = entityManager.find(UserContact.class,emailId);
        entityManager.remove(userContact);
        entityManager.getTransaction().commit();
        System.out.printf("Removed Contact ID = " + userContact.getUserId());

        return;
    }

    @Override
    public UserContact editUserDetails(UserContact userContact) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        UserContact updatedContact = null;
        try {
            updatedContact = entityManager.merge(userContact);
        }
        catch (IllegalArgumentException ex) {
            System.out.printf("Not able to Update Contact ID = " + userContact.getUserId());
        }
        entityManager.getTransaction().commit();
        System.out.printf("Updated Contact ID = " + userContact.getUserId());

        return updatedContact;
    }

    @Override
    public List<UserContact> getAllUsers() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<UserContact> userContactList = entityManager.createQuery("select u from UserContact u").getResultList();
        entityManager.getTransaction().commit();
        if (userContactList != null) {
            System.out.printf("No Contact Found...");
        }
        else {
            System.out.printf(userContactList.size() + " Contacts Found");
        }

        return userContactList;
    }
}

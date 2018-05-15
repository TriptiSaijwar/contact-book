package repository;

import models.UserContact;
import play.Logger;
import play.db.jpa.JPA;

import javax.persistence.TypedQuery;
import java.util.List;

//import javax.persistence.EntityManager;

//import javax.persistence.EntityManager;

/**
 * Created by tripti on 01/04/18.
 */

public class ContactBookRepository implements IContactBookRepository {

//    EntityManagerFactory entityManagerFactory;
//    EntityManager entityManager;

    ContactBookRepository() {
        //root method to create EntityManager
//        entityManagerFactory = Persistence.createEntityManagerFactory("defaultPersistenceUnit");
    }

    @Override
    public UserContact createUser(UserContact userContact) {
        try {
            // JPA method to create EntityManager without creating EntityManagerFactory here
            //            entityManager = entityManagerFactory.createEntityManager();
            return JPA.withTransaction("default",true, () -> {
                TypedQuery<UserContact> query = JPA.em().createQuery("select u from UserContact u where u.email = :email",UserContact.class);
                query.setParameter("email",userContact.getEmail());
                try {
                    UserContact userContactInDb = query.getSingleResult();
                    System.out.printf("Already Persist with Contact ID = " + userContactInDb.getUserId());
                    return null;
                } catch (Exception e) {
                    JPA.em().getTransaction().begin();
                    JPA.em().persist(userContact);
                    JPA.em().getTransaction().commit();
                    System.out.printf("Generated Contact ID = " + userContact.getUserId());
                    return userContact;
                }
            });
        }
        catch (Throwable throwable ) {
            Logger.error("{} {} \n{}", "error while finding user onject for", userContact.getEmail(), throwable.getCause());
        }
        return null;
    }

    @Override
    public void deleteUser(String emailId) {
//        entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//        UserContact userContact = entityManager.find(UserContact.class,emailId);
//        entityManager.remove(userContact);
//        entityManager.getTransaction().commit();
//        System.out.printf("Removed Contact ID = " + userContact.getUserId());

        return;
    }

    @Override
    public UserContact editUserDetails(UserContact userContact) {
//        entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//        UserContact updatedContact = null;
//        try {
//            updatedContact = entityManager.merge(userContact);
//        }
//        catch (IllegalArgumentException ex) {
//            System.out.printf("Not able to Update Contact ID = " + userContact.getUserId());
//        }
//        entityManager.getTransaction().commit();
//        System.out.printf("Updated Contact ID = " + userContact.getUserId());
//
//        return updatedContact;
        return null;
    }

    @Override
    public List<UserContact> getAllUsers() {
//        entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//        List<UserContact> userContactList = entityManager.createQuery("select u from UserContact u").getResultList();
//        entityManager.getTransaction().commit();
//        if (userContactList != null) {
//            System.out.printf("No Contact Found...");
//        }
//        else {
//            System.out.printf(userContactList.size() + " Contacts Found");
//        }
//
//        return userContactList;
        return null;
    }
}

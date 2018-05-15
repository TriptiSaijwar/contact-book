package services;

import models.UserContact;
import repository.IContactBookRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by tripti on 05/03/18.
 */

public class ContactBookService implements IContactBookService {

    @Inject
    IContactBookRepository mContactBookRepository;
    @Override
    public UserContact createUser(UserContact userContact) {
        return mContactBookRepository.createUser(userContact);
    }

    @Override
    public void deleteUser(String emailId) {
        mContactBookRepository.deleteUser(emailId);
    }

    @Override
    public UserContact editUserDetails(UserContact userContact) {
        return mContactBookRepository.editUserDetails(userContact);
    }

    @Override
    public List<UserContact> getAllUsers() {
        return mContactBookRepository.getAllUsers();
    }
}

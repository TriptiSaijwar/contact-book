package repository;

import com.google.inject.ImplementedBy;
import models.UserContact;

import java.util.List;

/**
 * Created by tripti on 01/04/18.
 */
@ImplementedBy(ContactBookRepository.class)
public interface IContactBookRepository {
    UserContact createUser(UserContact userContact);
    void deleteUser(String emailId);
    UserContact editUserDetails(UserContact userContact);
    List<UserContact> getAllUsers();
}

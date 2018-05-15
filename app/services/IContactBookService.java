package services;

import com.google.inject.ImplementedBy;
import models.UserContact;

import java.util.List;

/**
 * Created by tripti on 05/03/18.
 */

@ImplementedBy(ContactBookService.class)
public interface IContactBookService {
    UserContact createUser(UserContact userContact);
    void deleteUser(String emailId);
    UserContact editUserDetails(UserContact userContact);
    List<UserContact> getAllUsers();
}

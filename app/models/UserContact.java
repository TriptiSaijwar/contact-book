package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import play.Logger;
import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tripti on 05/03/18.
 */

@Entity
@Table(name = "user_contact_book")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserContact extends TemporalModel {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static UserContact findUserWithEmailId(String email) {
        try {
            return JPA.withTransaction(() -> {
                TypedQuery<UserContact> query = JPA.em().createQuery("select u from UserContact u where u.email = :email",UserContact.class);
                query.setParameter("email",email);
                try {
                    return query.getSingleResult();
                }
                catch (Exception ex) {
                    return null;
                }
            });
        }
        catch (Throwable throwable) {
            Logger.error("{} {} \n{}", "error while finding user contact with email => ", email, throwable.getCause());
        }
        return null;
    }

    public static List<UserContact> getAllUsers() {
        try {
            return JPA.withTransaction(() -> {
                TypedQuery<UserContact> query = JPA.em().createQuery("select u from UserContact u",UserContact.class);
                try {
                    return query.getResultList();
                }
                catch (Exception ex) {
                    return null;
                }
            });
        }
        catch (Throwable throwable) {
            Logger.error("{} \n{}", "error while fetching user contacts", throwable.getCause());
        }
        return null;
    }
}

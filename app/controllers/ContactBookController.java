package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.UserContact;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.IContactBookService;

import javax.inject.Inject;

/**
 * Created by tripti on 05/03/18.
 */

public class ContactBookController extends Controller {

    @Inject
    IContactBookService mContactBookService;

    public Result getView() {
        return ok(views.html.contactBook.render());
    }

    public Result getAll() {
        return ok(Json.toJson(mContactBookService.getAllUsers()));
    }

    public Result add(String emailId) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("No Data Received");
        }
        UserContact userContact;
        try {
            userContact = Json.fromJson(json, UserContact.class);
        }
        catch (RuntimeException ex) {
            return badRequest("Data Received is wrong");
        }

        return ok(Json.toJson(mContactBookService.createUser(userContact)));
    }

    public Result delete(String emailId) {
        try {
            mContactBookService.deleteUser(emailId);
        }
        catch (RuntimeException ex ) {
            return badRequest("Data Received is wrong");
        }
        return ok("Contact deleted");
    }

    public Result edit(String emailId) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("No Data Received");
        }
        UserContact userContact;
        try {
            userContact = Json.fromJson(json, UserContact.class);
        }
        catch (RuntimeException ex) {
            return badRequest("Data Received is wrong");
        }
        return ok(Json.toJson(mContactBookService.editUserDetails(userContact)));
    }

}

package com.arseniisemenov.springapp.controllers;

import com.arseniisemenov.springapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller // Marks this class as a Spring MVC controller
public class DataController {
    @Autowired // Injects the DBController dependency
    DBController dbController;

    // Handles GET requests for "/" or "/index"
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView getUsers() {
        // Retrieve all users from the database
        List<User> users = dbController.queryAllUsers();
        // Return the "index" view with the list of users
        return new ModelAndView("index", "users", users);
    }

    // Handles GET requests for "/user/{id}" to display a specific user
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ModelAndView getUser(@PathVariable(value="id") int id) {
        // Retrieve the user from the database using the provided id
        User user = dbController.queryUser(id);
        // Return the "user/user" view with the user data
        return new ModelAndView("user/user", "user", user);
    }

    // Handles GET requests for "/create" to display the user creation form
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getCreateView() {
        // Return the "create/create" view
        return "create/create";
    }

    // Handles POST requests for "/create" to create a new user
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RedirectView createUser(
        @RequestParam String name,
        @RequestParam String phone,
        @RequestParam String email,
        @RequestParam String blog,
        @RequestParam String note
    ) {
        // Create a new User object and set its properties
        User user = new User();
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setBlog(blog);
        user.setNote(note);

        // Add the new user to the database
        dbController.addUser(user);
        // Redirect to the home page after creation
        return new RedirectView("/");
    }

    // Handles GET requests for "/update/{id}" to display the user edit form
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView getEditView(@PathVariable(value="id") int id) {
        // Retrieve the user from the database using the provided id
        User user = dbController.queryUser(id);
        // Return the "edit/edit" view with the user data for editing
        return new ModelAndView("edit/edit", "user", user);
    }

    // Handles POST requests for "/update/{id}" to update an existing user
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public RedirectView updateUser(
            @PathVariable(value="id") int id,
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam String blog,
            @RequestParam String note
    ) {
        // Create a new User object with updated information
        User user = new User();
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setBlog(blog);
        user.setNote(note);
        user.setId(id);

        // Update the user in the database
        dbController.updateUser(user);
        // Redirect to the home page after updating
        return new RedirectView("/");
    }

    // Handles GET requests for "/delete/{id}" to delete a user
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public RedirectView deleteUser(@PathVariable(value="id") int id) {
        // Delete the user from the database using the provided id
        dbController.deleteUser(id);
        // Redirect to the home page after deletion
        return new RedirectView("/");
    }

    // Handles GET requests for "/find" to search for users by name
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ModelAndView findUsers(@RequestParam("name") String name) {
        // Find users in the database that match the provided name
        List<User> users = dbController.findUsers(name);
        // Return the "index" view with the list of found users
        return new ModelAndView("index", "users", users);
    }
}

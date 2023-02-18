package com.example.charitylink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;

@Controller
@RequestMapping(path="/api")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/user/add")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String username,
                                           @RequestParam String password, @RequestParam String email,
                                           @RequestParam(required = false, defaultValue = "") String addressLine1,
                                           @RequestParam(required = false, defaultValue = "") String addressLine2,
                                           @RequestParam(required = false, defaultValue = "") String city,
                                           @RequestParam(required = false, defaultValue = "") String state,
                                           @RequestParam(required = false, defaultValue = "") String zip, @RequestParam String date,
                                           @RequestParam(required = false, defaultValue = "-1") String companyID) {
        Date d = java.sql.Date.valueOf(date);
        User n = new User(name, username, password, email, addressLine1, addressLine2, city, state, Integer.parseInt(zip),
                java.sql.Date.valueOf(date), Integer.parseInt(companyID));
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/user/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}

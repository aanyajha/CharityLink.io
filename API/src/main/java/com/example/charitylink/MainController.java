package com.example.charitylink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String username,
                                           @RequestParam String password, @RequestParam String email,
                                           @RequestParam String addressLine1, @RequestParam String addressLine2,
                                           @RequestParam String city, @RequestParam String state,
                                           @RequestParam String zip, @RequestParam String date,
                                           @RequestParam(required = false, defaultValue = "-1") String companyID) {
        User n = new User(name, username, password, email, addressLine1, addressLine2, city, state, Integer.parseInt(zip),
                java.sql.Date.valueOf(date), Integer.parseInt(companyID));
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}

package com.example.charitylink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path="/api")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping(path="/user/add")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String username,
                                           @RequestParam String password, @RequestParam String email,
                                           @RequestParam(required = false, defaultValue = "") String addressLine1,
                                           @RequestParam(required = false, defaultValue = "") String addressLine2,
                                           @RequestParam(required = false, defaultValue = "") String city,
                                           @RequestParam(required = false, defaultValue = "") String state,
                                           @RequestParam(required = false, defaultValue = "") String zip, @RequestParam String date,
                                           @RequestParam(required = false, defaultValue = "-1") String companyID) {
        User user = new User(name, username, password, email, addressLine1, addressLine2, city, state, Integer.parseInt(zip),
                java.sql.Date.valueOf(date), Integer.parseInt(companyID));
        userRepository.save(user);
        return "Saved";
    }

    @GetMapping(path="/user/all") //Need to remove this at some point
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/item/add")
    public @ResponseBody String addNewItem(@RequestParam Integer userID, @RequestParam String name,
                                           @RequestParam Integer state, @RequestParam Integer numItems,
                                           @RequestParam String hashtags, @RequestParam String location) {
        List<Integer> maxIndex = itemRepository.findMaxItemIdByUser(userID);
        Integer itemID = 0;
        if (maxIndex.size() > 0) {
            itemID = maxIndex.get(0);
        }
        String s = "";
        if (state == 0) {
            s = "REQUESTED";
        } else if (state == 1) {
            s = "INSTOCK";
        } else if (state == 2) {
            s = "INPROGRESS";
        } else {
            s = "UNKNOWN";
        }
        Item item = new Item(userID, itemID + 1, name, s, numItems, hashtags, location);
        itemRepository.save(item);
        return "Saved";
    }

    @GetMapping(path = "/item/all") //Need to remove this at some point
    public @ResponseBody Iterable<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @PostMapping(path = "/event/add")
    public @ResponseBody String addNewEvent(@RequestParam String title, @RequestParam String description,
                                            @RequestParam String location, @RequestParam String date) {
        Event event = new Event(title, description, location, java.sql.Date.valueOf(date));
        eventRepository.save(event);
        return "Saved";
    }
}

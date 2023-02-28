package com.example.charitylink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/api")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private LocationRepository locationRepository;

    @PostMapping(path="/user/add")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String username,
                                           @RequestParam String password, @RequestParam String email,
                                           @RequestParam(required = false, defaultValue = "-1") String locationID, @RequestParam String date,
                                           @RequestParam(required = false, defaultValue = "-1") String companyID) {
        List<Integer> userIdList = userRepository.findUserIdByEmail(email);
        if (userIdList.size() > 0) {
            return "Email already exists";
        }
        User user = new User(name, username, password, email, java.sql.Date.valueOf(date), Integer.parseInt(companyID), Integer.parseInt(locationID));
        userRepository.save(user);
        return "Saved";
    }

    @GetMapping(path = "/user/login/email")
    public @ResponseBody User loginFromEmail(@RequestParam String email, @RequestParam String password) {
        List<Integer> userIdList = userRepository.findUserIdByEmail(email);
        if (userIdList.size() == 0) {
            return null;
        }
        Optional<User> user = userRepository.findById(userIdList.get(0));
        if (user.get().getPassword().equals(password)) {
            return userRepository.findById(userIdList.get(0)).get();
        } else {
            return null;
        }
    }

    @GetMapping(path = "/user/login/username")
    public @ResponseBody User loginFromUsername(@RequestParam String username, @RequestParam String password) {
        List<Integer> userIdList = userRepository.findUserIdByUsername(username);
        if (userIdList.size() == 0) {
            return null;
        }
        Optional<User> user = userRepository.findById(userIdList.get(0));
        if (user.get().getPassword().equals(password)) {
            return userRepository.findById(userIdList.get(0)).get();
        } else {
            return null;
        }
    }

    @DeleteMapping(path = "/user/delete")
    public @ResponseBody String deleteUserByEmail(@RequestParam String email) {
        List<Integer> userIdList = userRepository.findUserIdByEmail(email);
        if (userIdList.size() == 0) {
            return "Invalid Email";
        }
        long count = userRepository.count();
        userRepository.deleteById(userIdList.get(0));
        if (count > userRepository.count()) {
            return "Email = " + email + "; ID = " + userIdList.get(0) + "; Successfully deleted";
        } else {
            return "Error";
        }
    }

    @GetMapping(path="/user/all") //Need to remove this at some point
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/item/add")
    public @ResponseBody String addNewItem(@RequestParam Integer userID, @RequestParam String name,
                                           @RequestParam Integer state, @RequestParam Integer numItems,
                                           @RequestParam String hashtags, @RequestParam Integer location) {
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

    @DeleteMapping(path = "/item/delete")
    public @ResponseBody String deleteItemById(@RequestParam Integer itemId, @RequestParam Integer userId) {
        long count = itemRepository.count();
        itemRepository.deleteByItemIdAndUserId(itemId, userId);
        if (count > itemRepository.count()) {
            return "ItemID = " + itemId + "; UserID = " + userId + "; Successfully deleted";
        } else {
            return "Item does not exist";
        }
    }

    @GetMapping(path = "/item/all") //Need to remove this at some point
    public @ResponseBody Iterable<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @PostMapping(path = "/event/add")
    public @ResponseBody String addNewEvent(@RequestParam String title, @RequestParam String description,
                                            @RequestParam Integer locationID, @RequestParam String date) {
        Event event = new Event(title, description, locationID, java.sql.Date.valueOf(date));
        eventRepository.save(event);
        return "Saved";
    }

    @PostMapping(path = "/location/add")
    public @ResponseBody Integer addNewLocation(@RequestParam String addressLine1, @RequestParam(required = false, defaultValue = "") String addressLine2,
                                                @RequestParam String city, @RequestParam String state,
                                                @RequestParam Integer zip) {
        Location location = new Location(addressLine1, addressLine2, city, state, zip);
        locationRepository.save(location);
        return location.getId();
    }
}
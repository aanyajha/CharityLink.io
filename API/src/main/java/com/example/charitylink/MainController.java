package com.example.charitylink;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
                                           @RequestParam(required = false, defaultValue = "-1") String companyID, @RequestParam Integer userType) {
        List<Integer> userIdList = userRepository.findUserIdByEmail(email);
        if (userIdList.size() > 0) {
            return "Email already exists";
        }
        User user = new User(name, username, password, email, java.sql.Date.valueOf(date), Integer.parseInt(companyID), Integer.parseInt(locationID), userType);
        userRepository.save(user);
        if (user.getUserType() == 4) {
            user.setCompanyID(user.getId());
            userRepository.save(user);
        }
        return "Saved";
    }

    @GetMapping(path = "/user/login/email")
    public @ResponseBody User loginFromEmail(@RequestParam String email, @RequestParam String password) {
        List<Integer> userIdList = userRepository.findUserIdByEmail(email);
        if (userIdList.size() == 0) {
            return new User();
        }
        Optional<User> user = userRepository.findById(userIdList.get(0));
        if (user.get().getPassword().equals(password)) {
            return userRepository.findById(userIdList.get(0)).get();
        } else {
            return new User();
        }
    }

    @GetMapping(path = "/user/login/username")
    public @ResponseBody User loginFromUsername(@RequestParam String username, @RequestParam String password) {
        List<Integer> userIdList = userRepository.findUserIdByUsername(username);
        if (userIdList.size() == 0) {
            return new User();
        }
        Optional<User> user = userRepository.findById(userIdList.get(0));
        if (user.get().getPassword().equals(password)) {
            return userRepository.findById(userIdList.get(0)).get();
        } else {
            return new User();
        }
    }

    @PutMapping(path = "/user/update")
    public @ResponseBody User updateUser(@RequestParam(required = false) String name, @RequestParam(required = false) String username,
                                           @RequestParam(required = false) String password, @RequestParam String email,
                                           @RequestParam(required = false) String locationID, @RequestParam(required = false) String date,
                                           @RequestParam(required = false) String companyID, @RequestParam(required = false) Integer userType) {
        List<Integer> userIdList = userRepository.findUserIdByEmail(email);
        if (userIdList.size() == 0) {
            return new User();
        }
        User user = userRepository.findById(userIdList.get(0)).get();
        if (name != null) user.setName(name);
        if (username != null) user.setUsername(username);
        if (password != null) user.setPassword(password);
        if (locationID != null) user.setLocationID(Integer.parseInt(locationID));
        if (date != null) user.setJoinDate(java.sql.Date.valueOf(date));
        if(companyID != null) user.setCompanyID(Integer.parseInt(companyID));
        if (userType != null) user.setUserType(userType);
        userRepository.save(user);
        return user;
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

    @GetMapping(path = "/user/find/company")
    public @ResponseBody Iterable<User> getUsersByCompanyId(@RequestParam String email) {
        List<Integer> userIdList = userRepository.findUserIdByEmail(email);
        if (userIdList.size() == 0) {
            return new ArrayList<User>();
        }
        User user = userRepository.findById(userIdList.get(0)).get();
        return userRepository.findAllByCompanyID(user.getCompanyID(), user.getUserType());
    }

    @GetMapping(path="/user/all") //Need to remove this at some point
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/item/add")
    public @ResponseBody String addNewItem(@RequestParam Integer userID, @RequestParam String name,
                                           @RequestParam Integer state, @RequestParam Integer numItems,
                                           @RequestParam String hashtags, @RequestParam(required = false, defaultValue = "-1") String location) {
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
        Item item = new Item(userID, itemID + 1, name, s, numItems, hashtags, Integer.parseInt(location));
        itemRepository.save(item);
        return "Saved";
    }

    @GetMapping(path = "/item/inventory")
    public @ResponseBody Iterable<Item> getInventory(@RequestParam Integer userID) {
        return itemRepository.findItemsByUserID(userID);
    }

    @DeleteMapping(path = "/item/delete")
    public @ResponseBody String deleteItemById(@RequestParam Integer itemId, @RequestParam Integer userId) {
        long count = itemRepository.count();
        itemRepository.deleteByItemIDAndUserID(itemId, userId);
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
                                            @RequestParam Integer locationID, @RequestParam String date,
                                            @RequestParam Integer companyID) {
        Event event = new Event(title, description, locationID, java.sql.Date.valueOf(date), companyID, "");
        eventRepository.save(event);
        return "Saved";
    }

    @DeleteMapping(path = "/event/delete")
    public @ResponseBody Event deleteEvent(@RequestParam Integer id) {
        Event event = eventRepository.findById(id).get();
        if (event == null) {
            return new Event();
        }
        eventRepository.deleteById(id);
        return event;
    }

    @PutMapping(path = "/event/rsvp")
    public @ResponseBody Event rsvpEvent(@RequestParam Integer id, @RequestParam List<Integer> userIds) {
        Event event = eventRepository.findById(id).get();
        if (event == null) {
            return new Event();
        }
        for (int i = 0; i < userIds.size(); i++) {
            if (i == 0 && event.getUserList().equals("")) {
                event.setUserList("" + userIds.get(i));
            } else {
                event.setUserList(event.getUserList() + "," + userIds.get(i));
            }
        }
        eventRepository.save(event);
        return event;
    }

    @GetMapping(path = "/event/all")
    public @ResponseBody Iterable<Event> getAllEvents() {
        return eventRepository.findAll();
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
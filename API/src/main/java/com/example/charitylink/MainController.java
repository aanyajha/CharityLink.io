package com.example.charitylink;

import com.google.api.client.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @PutMapping(path = "/request/existing")
    public @ResponseBody Request requestExisiting(@RequestParam Integer userID, @RequestParam Integer itemID,
                                                  @RequestParam Integer requester) {
        Item item = itemRepository.findItemByUserIDAndItemID(userID, itemID);
        Item newItem = addNewItem(requester, item.getName(), 0, item.getHashtags(),
                userRepository.findById(requester).get().getLocationID() + "", item.getImg());
        itemRepository.save(newItem);
        Request r = new Request(requester, newItem.getItemID(), item.getNumItems(), "DELIVERY");
        item.setNumItems(0);
        itemRepository.save(item);
        requestRepository.save(r);
        return r;
    }

    class ProfileTemp {
        public Integer companyID;
        public String name;
        public String statement;
        public String logo;
        public String location;

        public ProfileTemp(Integer companyID, String name, String statement, String logo, String location) {
            this.companyID = companyID;
            this.name = name;
            this.statement = statement;
            this.logo = logo;
            this.location = location;
        }
    }

    @GetMapping(path = "/profile/all/companies")
    public @ResponseBody Iterable<ProfileTemp> getProfileTemp() {
        ArrayList<Profile> profiles = Lists.newArrayList(profileRepository.findAll());
        ArrayList<ProfileTemp> returnVal = new ArrayList<>();
        for (Profile profile : profiles) {
            User u = userRepository.findById(profile.getCompanyID()).get();
            returnVal.add(new ProfileTemp(profile.getCompanyID(), u.getName(), profile.getStatement(), profile.getLogo(),
                    locationRepository.findById(u.getLocationID()).get().toString()));
        }
        return returnVal;
    }

    @PostMapping(path = "/feedback/add")
    public @ResponseBody Feedback addFeedback(@RequestParam String feedback, @RequestParam Integer userID) {
        Feedback f = new Feedback(feedback, userID);
        feedbackRepository.save(f);
        return f;
    }

    class Tuple {
        public String name;
        public Integer count;
        
        public Tuple (String name, Integer count) {
            this.name = name;
            this.count = count;
        }
    }

    @GetMapping(path = "/request/local")
    public @ResponseBody Iterable<Tuple> localRequests(@RequestParam Integer locationID, @RequestParam Integer maxDistance) {
        ArrayList<Request> requests = Lists.newArrayList(requestRepository.findAll());
        ArrayList<Request> search = new ArrayList<>();
        for (Request request : requests) {
            User u = userRepository.findById(request.getRequestor()).get();
            if (u == null) {
                continue;
            }
            if (u.getUserType() == 1) {
                search.add(request);
            }
        }
        requests.clear();
        requests.addAll(search);
        search.clear();
        while (requests.size() > 0) {
            for (int i = 0; i < requests.size(); i++) {
                Item item = itemRepository.findItemByUserIDAndItemID(requests.get(i).getRequestor(), requests.get(i).getItemID());
                if (item != null) {
                    Request r = requests.get(i);
                    r.setHashtags(item.getHashtags());
                    r.setName(item.getName());
                    r.setImg(item.getImg());
                    r.setLocation(item.getLocation());
                    search.add(r);
                }
                requests.remove(i);
                break;
            }
        }
        requests.addAll(search);
        search.clear();
        Location location = locationRepository.findById(locationID).get();
        if (location == null || location.getLatitude() == null || location.getLongitude() == null) {
            return null;
        }
        for (Request request : requests) {
            Location requestLoc = locationRepository.findById(request.getLocation()).get();
            request.setDistance(requestLoc.findDistance(location.getLatitude(), location.getLongitude()));
            if (request.getDistance() <= maxDistance) {
                search.add(request);
            }
        }
        requests.clear();
        requests.addAll(search);
        search.clear();
        ArrayList<Tuple> returnVal = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        for (Request request : requests) {
            if (map.containsKey(request.getName())) {
                map.replace(request.getName(), map.get(request.getName()) + 1);
            } else {
                map.put(request.getName(), 1);
            }
        }
        for (String name : map.keySet()) {
            returnVal.add(new Tuple(name, map.get(name)));
        }
        ArrayList<Tuple> temp = new ArrayList<>();
        int index = -1;
        int max = 0;
        while (returnVal.size() > 0) {
            index = 0;
            max = 0;
            for (int i = 0; i < returnVal.size(); i++) {
                if (returnVal.get(i).count > max) {
                    max = returnVal.get(i).count;
                    index = i;
                }
            }
            temp.add(returnVal.get(index));
            returnVal.remove(index);
        }
        return temp;
    }

    @PutMapping(path = "/request/update/address")
    public @ResponseBody Request editRequestAddress(@RequestParam Integer id, @RequestParam String location) {
        String[] locationAttributes = location.split(";");
        Integer loc;
        if (locationAttributes.length == 5) {
            loc = addNewLocation(locationAttributes[0], locationAttributes[1], locationAttributes[2], locationAttributes[3], Integer.parseInt(locationAttributes[4]));
        } else if (locationAttributes.length == 1) {
            loc = Integer.parseInt(locationAttributes[0]);
        } else {
            return null;
        }
        Request r = requestRepository.findById(id).get();
        if (r == null) {
            return null;
        }
        Item i = itemRepository.findItemByUserIDAndItemID(r.getRequestor(), r.getItemID());
        i.setLocation(loc);
        itemRepository.save(i);
        return r;
    }

    @PostMapping(path = "/request/add/user")
    public @ResponseBody Request addUserRequest(@RequestParam String name, @RequestParam String hashtags,
                                                @RequestParam Integer quantity, @RequestParam Integer requester,
                                                @RequestParam String location, @RequestParam Integer deliveryType) {
        String[] locationAttributes = location.split(";");
        Integer loc;
        if (locationAttributes.length == 5) {
            loc = addNewLocation(locationAttributes[0], locationAttributes[1], locationAttributes[2], locationAttributes[3], Integer.parseInt(locationAttributes[4]));
        } else if (locationAttributes.length == 1) {
            loc = Integer.parseInt(locationAttributes[0]);
        } else {
            return null;
        }
        Item item = addNewItem(requester, name, 0, hashtags, loc + "", "");
        String s = "";
        switch (deliveryType) {
            case 0:
                s = "F2F";
                break;
            case 1:
                s = "DELIVERY";
                break;
            case 2:
                s = "PUBLIC";
                break;
        }
        Request request = new Request(requester, item.getItemID(), quantity, s);
        requestRepository.save(request);
        return request;
    }

    @PostMapping(path = "/request/add")
    public @ResponseBody Request addRequest(@RequestParam Integer itemID, @RequestParam Integer requester,
                                            @RequestParam Integer quantity, @RequestParam Integer deliveryType) {
        String s = "";
        switch (deliveryType) {
            case 0:
                s = "F2F";
                break;
            case 1:
                s = "DELIVERY";
                break;
            case 2:
                s = "PUBLIC";
                break;
        }
        Request r = new Request(requester, itemID, quantity, s);
        requestRepository.save(r);
        return r;
    }

    @DeleteMapping(path = "/request/delete")
    public @ResponseBody String deleteRequest(@RequestParam Integer id) {
        Request r = requestRepository.findById(id).get();
        if (r == null) {
            return "Error";
        }
        requestRepository.deleteById(id);
        return "Deleted";
    }

    @GetMapping(path = "/request/all")
    public @ResponseBody Iterable<Request> getAllRequests(@RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String hashtags,
                                                          @RequestParam(required = false) String location,
                                                          @RequestParam(required = false) Integer maxDistance,
                                                          @RequestParam(required = false) Integer userID) {
        ArrayList<Request> requests = (userID == null) ? Lists.newArrayList(requestRepository.findAll()) : Lists.newArrayList(requestRepository.findAllByRequestor(userID));
        ArrayList<Request> search = new ArrayList<>();
        while (requests.size() > 0) {
            for (int i = 0; i < requests.size(); i++) {
                Item item = itemRepository.findItemByUserIDAndItemID(requests.get(i).getRequestor(), requests.get(i).getItemID());
                if (item != null) {
                    Request r = requests.get(i);
                    r.setHashtags(item.getHashtags());
                    r.setName(item.getName());
                    r.setImg(item.getImg());
                    r.setLocation(item.getLocation());
                    search.add(r);
                }
                requests.remove(i);
                break;
            }
        }
        requests.addAll(search);
        search.clear();
        for (Request r : requests) {
            Item i = itemRepository.findItemByUserIDAndItemID(r.getRequestor(), r.getItemID());
            if (i != null) {
                r.setHashtags(i.getHashtags());
                r.setName(i.getName());
                r.setImg(i.getImg());
                r.setLocation(i.getLocation());
            } else {

            }
        }
        if (name != null) {
            for (Request r : requests) {
                if (r.getName().equals(name)) {
                    search.add(r);
                }
            }
            requests.clear();
            requests.addAll(search);
            search.clear();
        }
        if (hashtags != null) {
            String[] hashtagArr = hashtags.split(",");
            for (Request r : requests) {
                List<String> requestHashtags = Arrays.asList(r.getHashtags().split(","));
                for (int i = 0; i < hashtagArr.length; i++) {
                    if (requestHashtags.contains(hashtagArr[i])) {
                        search.add(r);
                        continue;
                    }
                }
            }
            requests.clear();
            requests.addAll(search);
            search.clear();
        }
        if (location != null && maxDistance != null) {
            String[] locationAttributes = location.split(";");
            Location loc = null;
            if (locationAttributes.length == 5) {
                loc = new Location(locationAttributes[0], locationAttributes[1], locationAttributes[2], locationAttributes[3],
                        Integer.parseInt(locationAttributes[4]));
            } else if (locationAttributes.length == 2) {
                loc = new Location(Double.parseDouble(locationAttributes[0]), Double.parseDouble(locationAttributes[1]));
            }
            if (loc != null) {
                Double min = Double.MAX_VALUE;
                int index = 0;
                while (requests.size() > 0) {
                    index = -1;
                    min = Double.MAX_VALUE;
                    for (int i = 0; i < requests.size(); i++) {
                        Integer locationID = requests.get(i).getLocation();
                        if (locationID == -1 || locationID == null) {
                            requests.remove(i);
                            break;
                        }
                        Location requestLoc = locationRepository.findById(locationID).get();
                        if (requestLoc.getLongitude() == null || requestLoc.getLatitude() == null) {
                            requests.remove(i);
                            break;
                        }
                        Double distance = loc.findDistance(requestLoc.getLatitude(), requestLoc.getLongitude());
                        if (distance >= maxDistance) {
                            requests.remove(i);
                            break;
                        }
                        if (distance < min) {
                            min = loc.findDistance(requestLoc.getLatitude(), requestLoc.getLongitude());
                            index = i;
                        }
                    }
                    if (index != -1) {
                        search.add(requests.get(index));
                        requests.remove(index);
                    }
                }
                requests.clear();
                requests.addAll(search);
                search.clear();
                for (int i = 0; i < requests.size(); i++) {
                    Location itemLoc = locationRepository.findById(requests.get(i).getLocation()).get();
                    requests.get(i).setDistance(loc.findDistance(itemLoc.getLatitude(), itemLoc.getLongitude()));
                }
            }
        }
        return requests;
    }

    @PostMapping(path = "delivery/add")
    public @ResponseBody Delivery addDelivery(@RequestParam Integer requesterID, @RequestParam Integer donator, 
                                          @RequestParam Integer state, @RequestParam boolean delivered) {
        String temp = "";
        if (state == 0) {
            temp = "INPROGRESS";
            delivered = false;
        } else if (state == 1) {
            temp = "DELIVERED";
            delivered = true;
        }
        Delivery delivery = new Delivery(donator, requesterID, temp, delivered);
        deliveryRepository.save(delivery);
        return delivery;
    }


    @GetMapping(path = "delivery/requester")
    public @ResponseBody Iterable<Delivery> deliveryByRequester(@RequestParam Integer requester) {
        return deliveryRepository.findAllByRequester(requester);
    }

    @GetMapping(path = "delivery/donator")
    public @ResponseBody Iterable<Delivery> deliveryByDonator(@RequestParam Integer donator) {
        return deliveryRepository.findAllByDonator(donator);
    }


    @DeleteMapping(path = "/delivery/delete")
    public @ResponseBody String deleteDelivery(@RequestParam Integer id) {
        Delivery delivery = deliveryRepository.findById(id).orElse(null);
        if (delivery == null) {
            return "Error: delivery not found";
        }

        if (delivery.getDelivered()) {
            // Delete the delivery
            deliveryRepository.deleteById(id);

            // Remove the delivery from requests
            Iterable<Request> requests = requestRepository.findAllByDonatorAndRequester(delivery.getDonator(), delivery.getRequester());
            for (Request request : requests) {
                if (request.getDelivered()) {
                    requestRepository.deleteById(request.getId());
                }
            }

            return "Deleted";
        } else {
            return "Error: delivery is not yet delivered";
        }
     }

    @DeleteMapping(path = "/delivery/cancel")
    public @ResponseBody String cancelDelivery(@RequestParam Integer id) {
        Delivery delivery = deliveryRepository.findById(id).orElse(null);
        if (delivery == null) {
            return "Error: delivery not found";
        }
        deliveryRepository.deleteById(id);
        return "Deleted";
    }


    @GetMapping(path = "/email/suspicious")
    public @ResponseBody String suspicious(@RequestParam String email) {
        List<Integer> userIdList = userRepository.findUserIdByEmail(email);
        if (userIdList.size() == 0) {
            return "Invalid Email";
        }
        try {
            new SendEmail().sendMail(email, "Suspicious Activity on your Charity Link Account",
                    "Someone has tried to login to your CharityLink account 10 times and failed.");
            return "Sent";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "failed";
        }
    }

    
    @GetMapping(path = "/password/reset")
    public @ResponseBody String passwordReset(@RequestParam String email) {
        List<Integer> userIdList = userRepository.findUserIdByEmail(email);
        if (userIdList.size() == 0) {
            return "Invalid Email";
        }
        String resetCode = "";
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        for (int i = 0; i < 6; i++) {
            resetCode += random.nextInt(10);
        }
        try {
            new SendEmail().sendMail(email, "Password Reset", resetCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        User user = userRepository.findById(userIdList.get(0)).get();
        user.setPasswordToken(resetCode);
        userRepository.save(user);
        return "Token Sent";
    }

    @GetMapping(path = "/password/check")
    public @ResponseBody String checkToken(@RequestParam String email, @RequestParam String token) {
        List<Integer> userIdList = userRepository.findUserIdByEmail(email);
        if (userIdList.size() == 0) {
            return "Invalid Email";
        }
        User user = userRepository.findById(userIdList.get(0)).get();
        if (user.getPasswordToken().equals(token)) {
            user.setPasswordToken("");
            userRepository.save(user);
            return "Valid";
        } else {
            return "Invalid";
        }
    }

    @GetMapping(path = "/feedback")
    public @ResponseBody String sendFeedbackEmail(@RequestParam("emailBody") String emailBody) {
    try {
        new SendEmail().sendMail("aanyajha121@gmail.com", "Feedback", emailBody);
    } catch (Exception e) {
        System.out.println(e.getMessage());
        return "Error sending feedback";
    }
    return "Feedback sent";
    }

    
    @PostMapping(path = "/profile/add")
    public @ResponseBody Profile addNewProfile(@RequestParam Integer companyID, @RequestParam String statement,
                                               @RequestParam String logo) {
        Profile profile = new Profile(companyID, statement, logo);
        profileRepository.save(profile);
        return profile;
    }

    @PutMapping(path = "/profile/edit")
    public @ResponseBody Profile editProfile(@RequestParam Integer companyID, @RequestParam(required = false) String statement,
                                             @RequestParam(required = false) String logo) {
        Profile profile = profileRepository.findById(companyID).get();
        if (profile == null) {
            return new Profile();
        }
        if (statement != null) profile.setStatement(statement);
        if (logo != null) profile.setLogo(logo);
        profileRepository.save(profile);
        return profile;
    }

    @GetMapping(path = "/profile/get")
    public @ResponseBody Profile getProfile (@RequestParam Integer companyID) {
        Profile profile = profileRepository.findById(companyID).get();
        if (profile == null) {
            return new Profile();
        }
        return profile;
    }

    @GetMapping(path = "/profile/all")
    public @ResponseBody Iterable<Profile> getAllProfiles () {
        return profileRepository.findAll();
    }

    @GetMapping(path = "/user/get")
    public @ResponseBody User getUser(@RequestParam Integer id) {
        User user = userRepository.findById(id).get();
        if (user == null) {
            return new User();
        }
        return user;
    }

    @PostMapping(path="/user/add")
    public @ResponseBody User addNewUser(@RequestParam String name, @RequestParam String username,
                                           @RequestParam String password, @RequestParam String email,
                                           @RequestParam(required = false, defaultValue = "-1") String locationID, @RequestParam String date,
                                           @RequestParam(required = false, defaultValue = "-1") String companyID, @RequestParam Integer userType,
                                         @RequestParam(required = false, defaultValue = "-1") Integer donor) {
        List<Integer> userIdList = userRepository.findUserIdByEmail(email);
        if (userIdList.size() > 0) {
            return new User();
        }
        User user = new User(name, username, password, email, java.sql.Date.valueOf(date), Integer.parseInt(companyID), Integer.parseInt(locationID), userType);
        userRepository.save(user);
        if (user.getUserType() == 4) {
            user.setCompanyID(user.getId());
            userRepository.save(user);
            Profile profile = new Profile(user.getCompanyID(), "", "");
            profileRepository.save(profile);
        }
        return user;
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

    @GetMapping(path = "/item/inventory/autofill/hashtag")
    public @ResponseBody Iterable<String> getInventoryAutofillHashtag(@RequestParam Integer userID) {
        Iterable<Item> inventory = itemRepository.findItemsByUserID(userID);
        ArrayList<String> hashtags = new ArrayList<>();
        for (Item i : inventory) {
            for (String hashtag : i.getHashtags().split(",")) {
                if (!hashtags.contains(hashtag)) {
                    hashtags.add(hashtag);
                }
            }
        }
        return hashtags;
    }

    @GetMapping(path = "/item/inventory/autofill/name")
    public @ResponseBody Iterable<String> getInventoryAutofillName(@RequestParam Integer userID) {
        Iterable<Item> inventory = itemRepository.findItemsByUserID(userID);
        ArrayList<String> names = new ArrayList<>();
        for (Item i : inventory) {
            if (!names.contains(i.getName())) {
                names.add(i.getName());
            }
        }
        return names;
    }

    @GetMapping(path = "/item/catalog/autofill/hashtag")
    public @ResponseBody Iterable<String> getCatalogAutofillHashtag() {
        Iterable<Item> inventory = itemRepository.findAll();
        ArrayList<String> hashtags = new ArrayList<>();
        for (Item i : inventory) {
            /*if (i.getState().equals("INSTOCK")) {
                for (String hashtag : i.getHashtags().split(",")) {
                    if (!hashtags.contains(hashtag)) {
                        hashtags.add(hashtag);
                    }
                }
            }*/
            for (String hashtag : i.getHashtags().split(",")) {
                if (!hashtags.contains(hashtag)) {
                    hashtags.add(hashtag);
                }
            }
        }
        return hashtags;
    }

    @GetMapping(path = "/item/catalog/autofill/name")
    public @ResponseBody Iterable<String> getCatalogAutofillName() {
        Iterable<Item> inventory = itemRepository.findAll();
        ArrayList<String> names = new ArrayList<>();
        for (Item i : inventory) {
            /*if (i.getState().equals("INSTOCK")) {
                if (!names.contains(i.getName())) {
                    names.add(i.getName());
                }
            }*/
            if (!names.contains(i.getName())) {
                names.add(i.getName());
            }
        }
        return names;
    }

    /*
        Location String needs to come in format of:
        {addressLine1};{addressLine2};{city};{state};{zip}
        or:
        {latitude};{longitude}
     */

    @GetMapping(path = "/item/search")
    public @ResponseBody Iterable<Item> itemSearch(@RequestParam(required = false) String hashtags,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) Integer itemID,
                                                   @RequestParam(required = false) String location,
                                                   @RequestParam(required = false) Integer userID,
                                                   /*@RequestParam Integer state,*/
                                                   @RequestParam(required = false) Integer maxDistance) {
        if (itemID != null && userID != null) {
            ArrayList<Item> items = new ArrayList<>();
            Item item = itemRepository.findItemByUserIDAndItemID(userID, itemID);
            if (item != null) {
                items.add(item);
            }
            return items;
        }
        ArrayList<Item> inventory = (userID == null) ? Lists.newArrayList(itemRepository.findAll()) : Lists.newArrayList(itemRepository.findItemsByUserID(userID));
        ArrayList<Item> search = new ArrayList<>();
        /*if (state == 0) {
            for (Item i : inventory) {
                if (i.getState().equals("REQUESTED")) {
                    search.add(i);
                }
            }
            inventory.clear();
            inventory.addAll(search);
            search.clear();
        } else if (state == 1) {
            for (Item i : inventory) {
                if (i.getState().equals("INSTOCK")) {
                    search.add(i);
                }
            }
            inventory.clear();
            inventory.addAll(search);
            search.clear();
        }*/
        if (name != null) {
            for (Item i : inventory) {
                if (i.getName().equals(name)) {
                    search.add(i);
                }
            }
            inventory.clear();
            inventory.addAll(search);
            search.clear();
        }
        if (hashtags != null) {
            String[] hashtagArr = hashtags.split(",");
            for (Item i : inventory) {
                List<String> itemHashtags = Arrays.asList(i.getHashtags().split(","));
                for (int j = 0; j < hashtagArr.length; j++) {
                    if (itemHashtags.contains(hashtagArr[j])) {
                        search.add(i);
                        continue;
                    }
                }
            }
            inventory.clear();
            inventory.addAll(search);
            search.clear();
        }
        if (location != null) {
            String[] locationAttributes = location.split(";");
            Location loc = null;
            if (locationAttributes.length == 5) {
                loc = new Location(locationAttributes[0], locationAttributes[1], locationAttributes[2], locationAttributes[3],
                        Integer.parseInt(locationAttributes[4]));
            } else if (locationAttributes.length == 2) {
                loc = new Location(Double.parseDouble(locationAttributes[0]), Double.parseDouble(locationAttributes[1]));
            }
            if (loc != null) {
                Double min = Double.MAX_VALUE;
                int index = 0;
                while (inventory.size() > 0) {
                    index = -1;
                    min = Double.MAX_VALUE;
                    for (int i = 0; i < inventory.size(); i++) {
                        Integer locationID = inventory.get(i).getLocation();
                        if (locationID == -1) {
                            inventory.remove(i);
                            break;
                        }
                        Location itemLoc = locationRepository.findById(locationID).get();
                        if (itemLoc.getLongitude() == null || itemLoc.getLatitude() == null) {
                            inventory.remove(i);
                            break;
                        }
                        Double distance = loc.findDistance(itemLoc.getLatitude(), itemLoc.getLongitude());
                        if (maxDistance != null && distance >= maxDistance) {
                            inventory.remove(i);
                            break;
                        }
                        if (distance < min) {
                            min = loc.findDistance(itemLoc.getLatitude(), itemLoc.getLongitude());
                            index = i;
                        }
                    }
                    if (index != -1) {
                        search.add(inventory.get(index));
                        inventory.remove(index);
                    }
                }
                inventory.clear();
                inventory.addAll(search);
                search.clear();
                for (int i = 0; i < inventory.size(); i++) {
                    Location itemLoc = locationRepository.findById(inventory.get(i).getLocation()).get();
                    inventory.get(i).setDistance(loc.findDistance(itemLoc.getLatitude(), itemLoc.getLongitude()));
                }
            }
        }
        return inventory;
    }

    @PostMapping(path = "/item/add")
    public @ResponseBody Item addNewItem(@RequestParam Integer userID, @RequestParam String name,
                                           @RequestParam Integer numItems,
                                           @RequestParam String hashtags, @RequestParam(required = false, defaultValue = "-1") String location,
                                           @RequestParam(required = false, defaultValue = "") String img) {
        List<Integer> maxIndex = itemRepository.findMaxItemIdByUser(userID);
        Integer itemID = 0;
        if (maxIndex.size() > 0) {
            itemID = maxIndex.get(0);
        }
        Item item = new Item(userID, itemID + 1, name, numItems, hashtags, Integer.parseInt(location), img);
        itemRepository.save(item);
        return item;
    }

    @GetMapping(path = "/item/inventory")
    public @ResponseBody Iterable<Item> getInventory(@RequestParam Integer userID) {
        return itemRepository.findItemsByUserID(userID);
    }

    @PutMapping(path = "/item/edit")
    public @ResponseBody Item editItem(@RequestParam Integer userID, @RequestParam Integer itemID, @RequestParam(required = false) String name,
                                       /*@RequestParam(required = false) Integer state,*/ @RequestParam(required = false) Integer numItems,
                                       @RequestParam(required = false) String hashtags, @RequestParam(required = false) Integer locationID) {
        Item item = itemRepository.findItemByUserIDAndItemID(userID, itemID);
        if (name != null) item.setName(name);
        /*if (state != null) {
            if (state == 0) {
                item.setState("REQUESTED");
            } else if (state == 1) {
                item.setState("INSTOCK");
            } else if (state == 2) {
                item.setState("INPROGRESS");
            } else {
                item.setState("UNKNOWN");
            }
        }*/
        if (numItems != null) item.setNumItems(numItems);
        if (hashtags != null) item.setHashtags(hashtags);
        if (locationID != null) item.setLocation(locationID);
        itemRepository.save(item);
        return item;
    }

    @DeleteMapping(path = "/item/delete")
    public @ResponseBody String deleteItemById(@RequestParam Integer itemID, @RequestParam Integer userID) {
        Integer count = itemRepository.deleteByItemIDAndUserID(itemID, userID);
        if (count > 0) {
            return "ItemID = " + itemID + "; UserID = " + userID + "; Successfully deleted";
        } else {
            return "Item does not exist";
        }
    }

    @GetMapping(path = "/item/all") //Need to remove this at some point
    public @ResponseBody Iterable<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping(path = "/event/get")
    public @ResponseBody Event getEvent(@RequestParam Integer id) {
        Event event = eventRepository.findById(id).get();
        if (event == null) {
            return new Event();
        }
        return event;
    }

    @PostMapping(path = "/event/add")
    public @ResponseBody String addNewEvent(@RequestParam String title, @RequestParam String description,
                                            @RequestParam Integer locationID, @RequestParam String date,
                                            @RequestParam Integer companyID, @RequestParam String owners) {
        Event event = new Event(title, description, locationID, java.sql.Date.valueOf(date), companyID, "", owners);
        eventRepository.save(event);
        return "Saved";
    }

    @GetMapping(path = "/event/owned")
    public @ResponseBody Iterable<Event> getOwnedEvents(@RequestParam Integer userID) {
        Iterable<Event> events = eventRepository.findAll();
        ArrayList<Event> owned = new ArrayList<>();
        for (Event e : events) {
            if (Arrays.asList(e.getOwnerList().split(",")).contains(userID) || e.getCompanyId() == userID) {
                owned.add(e);
            }
        }
        return owned;
    }

    @PutMapping(path = "/event/owner")
    public @ResponseBody String addNewOwner(@RequestParam Integer id, @RequestParam Integer owner) {
        Event event = eventRepository.findById(id).get();
        event.setOwnerList(event.getOwnerList() + "," + owner);
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
    public @ResponseBody Iterable<Event> getAllEvents(@RequestParam(required = false) String location) {
        ArrayList<Event> events = Lists.newArrayList(eventRepository.findAll());
        ArrayList<Event> sorted = new ArrayList<>();
        if (location != null) {
            String[] locAttributes = location.split(";");
            Location loc = null;
            if (locAttributes.length == 5) {
                loc = new Location(locAttributes[0], locAttributes[1], locAttributes[2], locAttributes[3], Integer.parseInt(locAttributes[4]));
            } else if (locAttributes.length == 2) {
                loc = new Location(Double.parseDouble(locAttributes[0]), Double.parseDouble(locAttributes[1]));
            }
            if (loc != null) {
                Double min = Double.MAX_VALUE;
                int index = 0;
                while (events.size() > 0) {
                    index = -1;
                    min = Double.MAX_VALUE;
                    for (int i = 0; i < events.size(); i++) {
                        Integer locationID = events.get(i).getLocationID();
                        if (locationID == -1) {
                            events.remove(i);
                            break;
                        }
                        Location itemLoc = locationRepository.findById(locationID).get();
                        if (itemLoc.getLongitude() == null || itemLoc.getLatitude() == null) {
                            events.remove(i);
                            break;
                        }
                        if (loc.findDistance(itemLoc.getLatitude(), itemLoc.getLongitude()) < min) {
                            min = loc.findDistance(itemLoc.getLatitude(), itemLoc.getLongitude());
                            index = i;
                        }
                    }
                    if (index != -1) {
                        sorted.add(events.get(index));
                        events.remove(index);
                    }
                }
                events.clear();
                events.addAll(sorted);
            }
            for (int i = 0; i < events.size(); i++) {
                Location itemLoc = locationRepository.findById(events.get(i).getLocationID()).get();
                events.get(i).setDistance(loc.findDistance(itemLoc.getLatitude(), itemLoc.getLongitude()));
            }
        }
        return events;
    }

    @PostMapping(path = "/location/add")
    public @ResponseBody Integer addNewLocation(@RequestParam String addressLine1, @RequestParam(required = false, defaultValue = "") String addressLine2,
                                                @RequestParam String city, @RequestParam String state,
                                                @RequestParam Integer zip) {
        Location location = new Location(addressLine1, addressLine2, city, state, zip);
        locationRepository.save(location);
        return location.getId();
    }

    @GetMapping(path = "/location/get")
    public @ResponseBody Location getLocation(@RequestParam Integer id) {
        Location location = locationRepository.findById(id).get();
        if (location == null) {
            return new Location();
        }
        return location;
    }

    @PutMapping(path = "/location/update")
    public @ResponseBody Location updateLocation(@RequestParam Integer id, @RequestParam(required = false) String addressLine1,
                                                 @RequestParam(required = false) String addressLine2,
                                                 @RequestParam(required = false) String city, @RequestParam(required = false) String state,
                                                 @RequestParam(required = false) String zip) {
        Location location = locationRepository.findById(id).get();
        if (location == null) {
            return new Location();
        }
        if (addressLine1 != null) location.setAddressLine1(addressLine1);
        if (addressLine2 != null) location.setAddressLine2(addressLine2);
        if (city != null) location.setCity(city);
        if (state != null) location.setState(state);
        if (zip != null) location.setZip(Integer.parseInt(zip));
        locationRepository.save(location);
        return location;
    }

    
}
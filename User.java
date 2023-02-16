import java.util.Date;

public class User {
        private String name;
        private String username;
        private String password;
        private int numberDonated;
        private long userID;
        private String location;
        private Date joinDate;
        private int companyID;

        public User(String name, String username, String password, int numberDonated, long userID, String location, Date joinDate) {
            this.name = name;
            this.username = username;
            this.password = password;
            this.numberDonated = numberDonated;
            this.userID = userID;
            this.location = location;
            this.joinDate = joinDate;
            this.companyID = 0;
        }

        public User(String name, String username, String password, int numberDonated, long userID, String location, Date joinDate, int companyID) {
            this.name = name;
            this.username = username;
            this.password = password;
            this.numberDonated = numberDonated;
            this.userID = userID;
            this.location = location;
            this.joinDate = joinDate;
            this.companyID = companyID;
        }

        public User(String name, String username, String password, int numberDonated, long userID) {
            this.name = name;
            this.username = username;
            this.password = password;
            this.numberDonated = numberDonated;
            this.userID = userID;
            this.companyID = 0;
        }

        public String getName() {
            return name;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public int getNumberDonated() {
            return numberDonated;
        }

        public long getUserID() {
            return userID;
        }

        public String getLocation() {
            return location;
        }

        public Date getJoinDate() {
            return joinDate;
        }

        public int getCompanyID() {
            return companyID;
        }

        public void setCompanyID(int companyID) {
            this.companyID = companyID;
        }

        public void donate(int amount) {
            numberDonated += amount;
        }

        public void changePassword(String newPassword) {
            password = newPassword;
        }
}
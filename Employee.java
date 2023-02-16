import java.util.Date;

public class Employee extends User {
    private int employeeID;

    public Employee(String name, String username, String password, int numberDonated, long userID, String location, Date joinDate, int employeeID) {
        super(name, username, password, numberDonated, userID, location, joinDate);
        this.employeeID = employeeID;
    }

    public int getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

}

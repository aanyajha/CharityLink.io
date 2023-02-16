import java.util.ArrayList;
import java.util.Date;

public class Manager extends Employee {
    private int managerID;
    private ArrayList<Integer> employeeList;
    private ArrayList<String> managerEventList;

    public Manager(String name, String username, String password, int numberDonated, long userID, String location, Date joinDate, int employeeID, int managerID, ArrayList<Integer> employeeList, ArrayList<String> managerEventList) {
        super(name, username, password, numberDonated, userID, location, joinDate, employeeID);
        this.managerID = managerID;
        this.employeeList = employeeList;
        this.managerEventList = managerEventList;
    }


    public int getManagerID() {
        return managerID;
    }

    public ArrayList<Integer> getEmployeeList() {
        return employeeList;
    }

    public ArrayList<String> getManagerEventList() {
        return managerEventList;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public void setEmployeeList(ArrayList<Integer> employeeList) {
        this.employeeList = employeeList;
    }

    public void setManagerEventList(ArrayList<String> managerEventList) {
        this.managerEventList = managerEventList;
    }

    public void modifyInventory() {
        // method to modify inventory
    }

    public void modifyManagerEvents() {
        // method to modify manager events
    }

    public void addEmployee(int employeeID) {
        employeeList.add(employeeID);
    }

    public void removeEmployee(int employeeID) {
        employeeList.remove((Integer) employeeID);
    }
}

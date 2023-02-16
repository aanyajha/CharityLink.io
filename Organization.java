import java.util.ArrayList;
import java.util.Date;

public class Organization extends Manager {
    private String missionStatement;
    private ArrayList<Integer> managersList;
    private ArrayList<Event> listOfOrgEvents;


    public Organization(String name, String username, String password, int numberDonated, long userID, String location, Date joinDate, int employeeID, int managerID, ArrayList<Integer> employeeList, ArrayList<String> managerEventList, String missionStatement, ArrayList<Integer> managersList, ArrayList<Event> listOfOrgEvents) {
        super(name, username, password, numberDonated, userID, location, joinDate, employeeID, managerID, employeeList, managerEventList);
        this.missionStatement = missionStatement;
        this.managersList = managersList;
        this.listOfOrgEvents = listOfOrgEvents;
    }


    public String getMissionStatement() {
        return missionStatement;
    }

    public ArrayList<Integer> getManagersList() {
        return managersList;
    }

    public ArrayList<Event> getListOfOrgEvents() {
        return listOfOrgEvents;
    }

    public void setMissionStatement(String missionStatement) {
        this.missionStatement = missionStatement;
    }

    public void setManagersList(ArrayList<Integer> managersList) {
        this.managersList = managersList;
    }

    public void setListOfOrgEvents(ArrayList<Event> listOfOrgEvents) {
        this.listOfOrgEvents = listOfOrgEvents;
    }

    public void modifyManagers() {
        // method to modify managers
    }

    public void modifyOrgEvents() {
        // method to modify org events
    }
}


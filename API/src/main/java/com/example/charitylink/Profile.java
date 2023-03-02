package com.example.charitylink;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Profile {
    @Id
    Integer companyID;
    String statement;
    String logo;

    public Profile(Integer companyID, String statement, String logo) {
        this.companyID = companyID;
        this.statement = statement;
        this.logo = logo;
    }

    public Profile(){}

    public Integer getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Integer companyID) {
        this.companyID = companyID;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}

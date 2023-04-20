package com.example.charitylink;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Feedback {
    @Id
    @GeneratedValue
    private Integer id;
    private String feedback;

    public Feedback(String feedback) {
        this.feedback = feedback;
    }

    public Feedback() {};

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

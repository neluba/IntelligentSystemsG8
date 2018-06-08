package de.competition.thesis.constraints;

import de.competition.thesis.models.Configuration;

import java.io.Serializable;

public class Constraint implements Serializable{

    private String name;
    public Constraint(String name){
        this.name = name;
    }

    public boolean check(Configuration configuration){
       return true;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

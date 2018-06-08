package de.competition.thesis.models;

import java.io.Serializable;
import java.util.List;

public class Variant implements Serializable {
    private String name;
    private List<Operation> operations;

    public String getName() {
        return name;
    }

    public List<Operation> getOperations() {
        return operations;
    }
}

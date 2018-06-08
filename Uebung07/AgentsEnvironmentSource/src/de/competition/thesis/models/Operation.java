package de.competition.thesis.models;

import java.io.Serializable;
import java.util.List;

public class Operation implements Serializable {
    private int index;
    private List<Integer> durations;
    private List<String> resources;
    private String chosenResource;
    private int chosenDuration;
    private int[] chosenInterval;

    public int getIndex() {
        return index;
    }


    public List<String> getResources() {
        return resources;
    }

    public String getChosenResource() {
        return chosenResource;
    }

    public void setChosenResource(String chosenResource) {
        this.chosenResource = chosenResource;
    }

    public List<Integer> getDurations() {
        return durations;
    }

    public int getChosenDuration() {
        return chosenDuration;
    }

    public void setChosenDuration(int chosenDuration) {
        this.chosenDuration = chosenDuration;
    }

    public int[] getChosenInterval() {
        return chosenInterval;
    }

    public void setChosenInterval(int[] chosenInterval) {
        this.chosenInterval = chosenInterval;
    }

}

package de.competition.thesis.models;


import de.competition.thesis.ressources.Machine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Configuration implements Serializable {


    private List<Machine> machines;
    private List<Order> orders;

    public Configuration(){
        orders = new ArrayList<>();
        machines = new ArrayList<>();
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public void addMachine(Machine machine){
        machines.add(machine);
    }

    public Machine getMachineByName(String name){
        for(Machine machine: machines){
            if(machine.getName().equals(name)){
                return machine;
            }
        }
        return null;
    }


    @Override
    public int hashCode() {
        return machines.hashCode();
    }
}

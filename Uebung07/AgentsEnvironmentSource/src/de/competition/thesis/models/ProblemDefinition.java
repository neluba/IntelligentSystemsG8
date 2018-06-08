package de.competition.thesis.models;


import com.google.gson.annotations.Expose;
import de.competition.thesis.constraints.Constraint;
import de.competition.thesis.helper.Cloner;
import de.competition.thesis.ressources.Machine;

import java.io.Serializable;
import java.util.List;

public class ProblemDefinition implements Serializable {
    private List<Product> products;
    private List<String> resources;
    private List<Order> orders;

    @Expose(serialize = false)
    private List<Constraint> hardConstraints;

    @Expose(serialize = false)
    private List<Constraint> softConstraints;


    @Expose(serialize = false)
    private Configuration configuration;

    @Expose(serialize = false)
    private List<Machine> machines;


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
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

    public List<Constraint> getHardConstraints() {
        return hardConstraints;
    }

    public void setHardConstraints(List<Constraint> hardConstraints) {
        this.hardConstraints = hardConstraints;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Product getProductByName(String name){
        for(Product product: products){
            if(product.getName().equals(name)){
                return product;
            }
        }
        return null;
    }

    public List<Constraint> getSoftConstraints() {
        return softConstraints;
    }

    public void setSoftConstraints(List<Constraint> softConstraints) {
        this.softConstraints = softConstraints;
    }

    public void loadTempMachines(List<Machine> tempMachines) {
        machines.clear();
        Cloner cloner = new Cloner();
        for(Machine machine: tempMachines){
            Machine newMachine = (Machine) cloner.cloneObject(machine);
            machines.add(newMachine);
        }
    }

    public void loadTempOrders(List<Order> tempOrders) {
        orders.clear();
        Cloner cloner = new Cloner();
        for(Order order: tempOrders){
            Order newOrder = (Order) cloner.cloneObject(order);
            orders.add(newOrder);
        }
    }
}

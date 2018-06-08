package de.competition.thesis.ressources;

import de.competition.thesis.models.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Machine implements Serializable{
    HashMap<Integer, String[]> occupancyHashMap;
    public String name;
    private final String NOPE = "-------";
    private int[] currentInterval;

    public Machine(String name){
        occupancyHashMap = new HashMap<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Methode welche zunächst nach einem passenden Zeitslot sucht.
     * Danach wird dieser belegt
     * @param order
     * @param duration
     * @param operationName
     * @return
     */
    public int[] occupy(Order order, int duration, String operationName){
        List<int[]> freeSpaces = getFreeSpaces();
        currentInterval = new int[2];
        int nextPossibleMomentForOrder = order.getNextMoment();
        if(occupancyHashMap.size() == 0){
            int j = occupyMoments(1,nextPossibleMomentForOrder, duration, order.getOrderName(),operationName);
            currentInterval[1] = j-1;
            order.setNextMoment(j);
            return currentInterval;
        }
        for(int[] space : freeSpaces){
            int spaceSize = space[1]- space[0]+1;
            if(duration > spaceSize){
                continue;
            }
            if(nextPossibleMomentForOrder+duration-1 > space[1]){
               continue;
            }
            if(space[0]+duration-1 <= space[1]  ){
                int j = occupyMoments(space[0],nextPossibleMomentForOrder, duration, order.getOrderName(), operationName);
                currentInterval[1] = j-1;
                order.setNextMoment(j);
                return currentInterval;
            }
        }
        int j = occupyMoments(occupancyHashMap.size()+1,nextPossibleMomentForOrder, duration, order.getOrderName(), operationName);
        currentInterval[1] = j-1;
        order.setNextMoment(j);
        return currentInterval;

    }


    /**
     * Belegt ausgewählte Zeitslots
     * @param startMoment
     * @param nextPossibleMomentForOrder
     * @param duration
     * @param orderName
     * @param operationName
     * @return
     */
    private int occupyMoments(int startMoment, int nextPossibleMomentForOrder , int duration, String orderName, String operationName) {
        if(operationName.equals("v2 1/3")){
            String test = "";
        }
        if(nextPossibleMomentForOrder > startMoment) {
            for(int i = startMoment; i<= nextPossibleMomentForOrder; i++){
                occupancyHashMap.put(i, new String[]{NOPE, NOPE});
            }
            startMoment = nextPossibleMomentForOrder;
        }
        currentInterval[0]= startMoment;
        for(int i = 0; duration> i; i++){
            occupancyHashMap.put(startMoment+i, new String[]{orderName, operationName});
        }
        return startMoment+duration;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int max = occupancyHashMap.size();
        for(int i = 1; i<= max; i++){
            stringBuilder.append(occupancyHashMap.get(i)[0]+"\t");
        }
        return stringBuilder.toString();
    }

    /**
     * Bestimmt alle freien Zeitslots
     * @return
     */
    private List<int[]> getFreeSpaces(){
        List<int[]> freeSpacesList = new ArrayList<>();
        boolean first = true;
        int[] space = new int[2];
        for(int i = 1; i<= occupancyHashMap.size(); i++){
            if(occupancyHashMap.get(i)[0].equals(NOPE)){
                if(first){
                    space[0] = i;
                    space[1] = i;
                    first = false;
                }else{
                    space[1] = i;
                }
            }else{
                if(space[1] != 0) {
                    freeSpacesList.add(space);
                    first = true;
                    space = new int[2];
                }
            }
        }
        return freeSpacesList;
    }


    public void removeOrder(Order order) {
        for(int key: occupancyHashMap.keySet()){
            if(occupancyHashMap.get(key)[0].equals(order.getOrderName())){
                occupancyHashMap.put(key, new String[]{NOPE, NOPE});
            }
        }
        reduceOccupancyHashMap();
    }

    /**
     * Kürzt die Hashmap um nachstehende nullen
     */
    private void reduceOccupancyHashMap(){
        ArrayList<Integer> keys = new ArrayList<>(occupancyHashMap.keySet());
        for(int i=keys.size()-1; i>=0;i--){
            int key = keys.get(i);
            String value = occupancyHashMap.get(key)[0];
            if(value.equals(NOPE))
                occupancyHashMap.remove(key);
            else
                return;
        }
    }

    @Override
    public int hashCode() {
        return occupancyHashMap != null ? occupancyHashMap.hashCode() : 0;
    }
}

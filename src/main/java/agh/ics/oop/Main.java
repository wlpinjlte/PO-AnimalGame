package agh.ics.oop;


import agh.ics.oop.maps.GlobeMap;


public class Main {
    public static void main(String[] args) throws IllegalAccessException, InterruptedException {
        GlobeMap map=new GlobeMap(5,5);
        Simulation simulation=new Simulation(map);
        simulation.run();
    }
}
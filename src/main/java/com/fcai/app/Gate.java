package com.fcai.app;

import java.util.List;

public class Gate extends Thread {
    private final ParkingManager parking;
    private final String gateName;
    private final List<String[]> cars;
    private int carsServed = 0;
    public Gate(ParkingManager parking, String gateName, List<String[]> cars) {
        this.parking = parking;
        this.gateName = gateName;
        this.cars = cars;
    }

    public int getCarsServed() {
        return carsServed;
    }
    
    public final String getGateName() {
        return gateName;
    }

    @Override
    public void run() {
        for (String[] carData : cars) {
            String carID = carData[0];
            int arrivalTime = Integer.parseInt(carData[1]);
            int durationOfParking = Integer.parseInt(carData[2]);
            carsServed++;

            Car car = new Car(parking, carID, gateName, arrivalTime, durationOfParking);
            car.start();
            try {
                car.join();
            } catch (InterruptedException e) {
                System.out.println("Gate thread interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }
}

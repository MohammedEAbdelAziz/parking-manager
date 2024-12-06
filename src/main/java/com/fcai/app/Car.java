package com.fcai.app;

public class Car extends Thread {
    private final ParkingManager parking;
    private final String carID;
    private final int parkingDuration;
    private final String gate;
    private final int arrivalTime;
    public Car(ParkingManager parking,String carID, String gate, int arrivalTime, int parkingDuration) {
        this.parking = parking;
        this.carID = carID;
        this.gate = gate;
        this.arrivalTime = arrivalTime;
        this.parkingDuration = parkingDuration;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(arrivalTime * 1000);
            parking.attemptToPark(carID, gate, arrivalTime, parkingDuration);
        } catch (Exception e) {
            System.out.println("An error occurred for " + carID);
            Thread.currentThread().interrupt();
        }
    }
}

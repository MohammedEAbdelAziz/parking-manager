package com.fcai.app;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

class ParkingManager {
    private final Semaphore spots = new Semaphore(4);
    private final AtomicInteger currentParkedCars = new AtomicInteger(0);

    public void attemptToPark(String carID, String gate, int arrivalTime, int DurationOfParking) {
        try {
            System.out.println(carID + " from " + gate + " arrived at time " + arrivalTime);


            if (spots.availablePermits() == 0) {
                System.out.println(carID + " from " + gate + " waiting for a spot.");
            }


            spots.acquire();
            currentParkedCars.incrementAndGet();
            System.out.println(carID + " from " + gate + " parked. (Parking Status: " + (4 - spots.availablePermits()) + " spots occupied)");


            Thread.sleep(DurationOfParking * 1000);




            spots.release();
            currentParkedCars.decrementAndGet(); 

            System.out.println(carID + " from " + gate + " left after " + DurationOfParking + " units of time. (Parking Status: " + (4 - spots.availablePermits()) + " spots occupied)");

        } catch (InterruptedException e) {
            System.out.println("An error occurred for " + carID);
        }   
    }
    public int getCurrentParkedCars() {
        return currentParkedCars.get();
    }
}




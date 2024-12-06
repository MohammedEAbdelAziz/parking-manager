package com.fcai.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "input.txt";
        Map<String, List<String[]>> gateCarsMap = new HashMap<>();

        List<String> allowedGates = Arrays.asList("Gate 1", "Gate 2", "Gate 3");
        for (String gate : allowedGates) {
            gateCarsMap.put(gate, new ArrayList<>());
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                if (parts.length == 4) {
                    String gate = parts[0];
                    String carID = parts[1];
                    int arrivalTime = Integer.parseInt(parts[2].replace("Arrive ", ""));
                    int parkingDuration = Integer.parseInt(parts[3].replace("Parks ", ""));

                    if (allowedGates.contains(gate)) {
                        gateCarsMap.get(gate).add(new String[]{carID, String.valueOf(arrivalTime), String.valueOf(parkingDuration)});
                    } else {
                        System.out.println("Skipping unsupported gate: " + gate);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }

        ParkingManager parking = new ParkingManager();

        List<Gate> gateThreads = new ArrayList<>();
        for (String gate : allowedGates) {
            List<String[]> cars = gateCarsMap.get(gate);

            Gate gateThread = new Gate(parking, gate, cars);
            gateThreads.add(gateThread);
            gateThread.start();
        }

        for (Gate gateThread : gateThreads) {
            try {
                gateThread.join();
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted.");
                Thread.currentThread().interrupt();
            }
        }
        int totalCarsServed = 0;
        for (Gate gateThread : gateThreads) {
            totalCarsServed += gateThread.getCarsServed();
        }

        System.out.println("\nSimulation Complete");
        System.out.println("Total Cars Served: " + totalCarsServed);
        System.out.println("Current Cars in Parking: " + parking.getCurrentParkedCars());
        System.out.println("Details:-");
        for (Gate gateThread : gateThreads) {
            System.out.println(gateThread.getGateName() + " served " + gateThread.getCarsServed() + " cars.");
        }
    }
}

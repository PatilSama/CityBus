package com.example.citybus.ModelClases;

public class RvChangeNearBusStopModel {
    String nearStop,stopKm;
    public RvChangeNearBusStopModel(String nearStop, String stopKm)
    {
        this.nearStop=nearStop;
        this.stopKm=stopKm;
    }

    public String getNearStop()
    {
        return nearStop;
    }

    public String getStopKm()
    {
        return stopKm;
    }
}

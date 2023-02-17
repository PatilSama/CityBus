package com.example.citybus.ModelClases;

public class NearestBusStopModel {

    String stopName,stopDist,busTripNo;
    public NearestBusStopModel(String stopName, String stopDist, String busTripNo)
    {
        this.stopName=stopName;
        this.stopDist=stopDist;
        this.busTripNo=busTripNo;
    }

    public String getStopName()
    {
        return stopName;
    }
    public String getStopDist()
    {
        return stopDist;
    }
    public String getBusTripNo()
    {
        return busTripNo;
    }

}

package com.example.citybus.ModelClases;

public class FindBusesResultBottomSheetModel {
    String busName,busNumber,busArrivalTime,busFrom,busTo;
    public FindBusesResultBottomSheetModel(String busName, String busNumber, String busArrivalTime, String busFrom,String stopTo)
    {
        this.busName = busName;
        this.busNumber = busNumber;
        this.busArrivalTime = busArrivalTime;
        this.busFrom = busFrom;
        this.busTo = stopTo;
    }

    public String getBusName()
    {
        return busName;
    }


    public String getBusNumber()
    {
        return busNumber;
    }


    public String getBusArrivalTime()
    {
        return busArrivalTime;
    }


    public String getBusFrom()
    {
        return busFrom;
    }


    public String getBusTo()
    {
        return busTo;
    }
}
